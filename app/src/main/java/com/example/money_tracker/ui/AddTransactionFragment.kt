package com.example.money_tracker.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.money_tracker.*
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionFragment : Fragment() {
    lateinit var viewModel: TransactionDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting back button on toolbar
        viewModel=ViewModelProvider(this).get(TransactionDetailViewModel::class.java)
        val toolbar:MaterialToolbar=requireActivity().findViewById(R.id.transaction_toolbar)
        //toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener()
        {
            requireActivity().onBackPressed()
        }
        //setting date dialog box on the edit text the problem while using is this the edit text shows the date picker when clicked 2 ime
//        val calendar=Calendar.getInstance()
//        val myYear=calendar.get(Calendar.YEAR)
//        val myMonth=calendar.get(Calendar.MONTH)
//        val myDay=calendar.get(Calendar.DAY_OF_MONTH)
//        transDateEt.setOnClickListener()
//        {
//            val datePickerDialog=DatePickerDialog(requireActivity(),DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//                val ans:String="Date: "+dayOfMonth+"/"+(month+1)+"/ "+year
//                transDateEt.setText(ans)
//            },myYear,myMonth,myDay)
//            datePickerDialog.show()
//        }
        //textInputLayout6.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy")
        textInputLayout6.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", Date())
        textInputLayout7.editText?.transformIntoDatePicker(requireContext(),"dd/MM/yyyy", Date())
        textInputLayout9.editText?.transformIntoDatePicker(requireContext(),"dd/MM/yyyy", Date())
        //setting the list in category spinner using array adapter
        val category:MutableList<String> = mutableListOf()
        TransactionType.values().forEach{ category.add(it.name) }
        val myAdapter=ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,category)
        categorySpinner.adapter=myAdapter
        //setting the list in tag spinner using array adapter
        val tags:MutableList<String> = mutableListOf()
        Tag.values().forEach { tags.add(it.name) }
        val myAdapter2=ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,tags)
        tagSpinner.adapter=myAdapter2
        //setting view model
        val id=AddTransactionFragmentArgs.fromBundle(requireArguments()).id
        if(id!=0L)
        {
           disableAll()
        }
        viewModel.setId(id)
       viewModel.transact.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
           it?.let{saveData(it)}
       })
        //making the from date and to date edit text enable only when recurring transaction checkbox is clicked
        transFromDateEt.isEnabled=false
        transToDateEt.isEnabled=false
        //enabling the from date and to date when recurring check is marked
        recurringCheck.setOnCheckedChangeListener(){ _, isChecked->
            if(isChecked)
            {   transFromDateEt.isEnabled=true
                transToDateEt.isEnabled=true
            }
            else
            {
                transFromDateEt.isEnabled=false
                transToDateEt.isEnabled=false
            }
        }
        //setting on click listener on income button
        incomeBtn.setOnClickListener(){
            var flag=0
            val title=transTitleEt.text.toString()
            val amount=transAmountEt.text.toString()
            val date=transDateEt.text.toString()
            if(TextUtils.isEmpty(title)) flag=1
            else if(TextUtils.isEmpty(amount)) flag=1
            else if(TextUtils.isEmpty(date)) flag=1
            if(flag==1)
            {Toast.makeText(requireActivity(),"Fill All Mandatory Fields",Toast.LENGTH_SHORT).show()}
            else
            {
                //function to save the entered data in the data base
                insertOrUpdateIncome()
                requireActivity().onBackPressed()
            }
        }
        //setting on click listener on expense button
        expenseBtn .setOnClickListener(){
            var flag=0
            val title=transTitleEt.text.toString()
            val amount=transAmountEt.text.toString()
            val date=transDateEt.text.toString()
            if(TextUtils.isEmpty(title)) flag=1
            else if(TextUtils.isEmpty(amount)) flag=1
            else if(TextUtils.isEmpty(date)) flag=1
            if(flag==1)
            {Toast.makeText(requireActivity(),"Fill All Mandatory Fields",Toast.LENGTH_SHORT).show()}
            else
            {
                //function to save the entered data in the data base
                insertOrUpdateExpense()
               requireActivity().onBackPressed()
            }
        }
        transaction_toolbar.setOnMenuItemClickListener {
            when(it.itemId)
            {
                R.id.transaction_pencil ->{
                    enableAll()
                    true
                }
                R.id.transaction_delete ->{
                    alertDialogshow()
                    //viewModel.deleteTransaction()
                    //requireActivity().onBackPressed()
                    true
                }
                else -> false
            }
        }

    }
 //function to show alert dialog builder when delete button is clicked
    private fun alertDialogshow() {
        val builder=AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle("DELETE ITEM")
            setMessage("Do You Want To Delete The Item From List")
            builder.setNegativeButton("NO")
            {
                    DIALOG,WHICH->
            }
            builder.setPositiveButton("YES")
            {
                    dialog,which->
                viewModel.deleteTransaction()
                requireActivity().onBackPressed()
            }

        }
        val dialog=builder.create()
        dialog.show()
    }
//function to disable all the fields before user clicks on pencil icon
    private fun disableAll() {

        transTitleEt.isEnabled=false
        transAmountEt.isEnabled=false
        transDateEt.isEnabled=false
        transFromDateEt.isEnabled=false
        recurringCheck.isEnabled=false
        transToDateEt.isEnabled=false
        tagSpinner.isEnabled=false
        categorySpinner.isEnabled=false
        transCommentsEt.isEnabled=false
        incomeBtn.isEnabled=false
        expenseBtn.isEnabled=false
    }
    //function to enable all fields when user clicks on pencil icon
    private fun enableAll()
    {
        transTitleEt.isEnabled=true
        transAmountEt.isEnabled=true
        transDateEt.isEnabled=true
        transFromDateEt.isEnabled=true
        recurringCheck.isEnabled=true
        transToDateEt.isEnabled=true
        tagSpinner.isEnabled=true
        categorySpinner.isEnabled=true
        transCommentsEt.isEnabled=true
        incomeBtn.isEnabled=true
        expenseBtn.isEnabled=true
    }
    //function to save data when recycler view item is clicked
    private fun saveData(transaction: Transaction){

        transTitleEt.setText(transaction.title)
        transAmountEt.setText(transaction.amount.toString())
        transDateEt.setText(transaction.date)
        transFromDateEt.setText(transaction.fromDate)
        transToDateEt.setText(transaction.toDate)
        transCommentsEt.setText(transaction.comments)
        categorySpinner.setSelection(transaction.transactionType)
        tagSpinner.setSelection(transaction.tag)
    }
//function to pass the data to the database when expense button is clicked
    private fun insertOrUpdateExpense() {
        val transTitle:String=transTitleEt.text.toString()
        val transAmount:Float=-(transAmountEt.text.toString().toFloat())
        val transDate:String=transDateEt.text.toString()
        val day=Integer.parseInt(transDate.substring(0,2))
        val month=Integer.parseInt(transDate.substring(3,5))
        val year=Integer.parseInt(transDate.substring(6,10))
        val monthYear=(""+month+year).toLong()
        //val datePicker:Date=Date(year,month,day)
        val transFromDate:String=transFromDateEt.text.toString()
        val transToDate:String=transToDateEt.text.toString()
        val transType=categorySpinner.selectedItemPosition
        val transTag=tagSpinner.selectedItemPosition
        val transComments:String=transCommentsEt.text.toString()
        viewModel.saveTransaction(
            Transaction(
                viewModel.transactionId.value!!,
                transTitle,
                transAmount,
                transDate,
                day,
                month,
                year,
                monthYear,
                transType,
                //datePicker,
                transTag,
                transComments,
                transFromDate,
                transToDate,
                IncomeExpense.Expense.ordinal
            )
        )
    }
    //function to pass the data to the database when income button is clicked
    private fun insertOrUpdateIncome() {
        val transTitle:String=transTitleEt.text.toString()
        val transAmount:Float=transAmountEt.text.toString().toFloat()
        val transDate:String=transDateEt.text.toString()
        val day=Integer.parseInt(transDate.substring(0,2))
        val month=Integer.parseInt(transDate.substring(3,5))
        val year=Integer.parseInt(transDate.substring(6,10))
        val monthYear=(""+month+year).toLong()
        //val datePicker:Date=Date(year,month,day)
        val transFromDate:String=transFromDateEt.text.toString()
        val transToDate:String=transToDateEt.text.toString()
        val transType=categorySpinner.selectedItemPosition
        val transTag=tagSpinner.selectedItemPosition
        val transComments:String=transCommentsEt.text.toString()
        viewModel.saveTransaction(
            Transaction(
                viewModel.transactionId.value!!,
                transTitle,
                transAmount,
                transDate,
                day,
                month,
                year,
                monthYear,
                transType,
                transTag,
                transComments,
                transFromDate,
                transToDate,
                IncomeExpense.Income.ordinal
            )
        )
    }
//function to set calendar dialog on the edit text in add transaction fragment
    private fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
//                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }
}