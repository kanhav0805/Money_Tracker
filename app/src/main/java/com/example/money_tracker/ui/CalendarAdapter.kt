package com.example.money_tracker

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.money_tracker.data.Expense
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.daily_item_layout.*
import kotlinx.android.synthetic.main.fragment_user_detail.*

class CalendarAdapter(private val listener: (String) -> Unit):
    ListAdapter<Expense, CalendarAdapter.ViewHolder>(
        DiffCallback3()
    ){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_item_layout, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init{
            itemView.setOnClickListener{
                listener.invoke(getItem(adapterPosition).date)
            }
        }
  //bind function to bind the item layout
        fun bind(transaction: Expense){
            with(transaction){
                if(transaction.amount<0)
                {views.setBackgroundColor(ContextCompat.getColor(containerView.context,R.color.red))
                    amounts.setTextColor(ContextCompat.getColor(containerView.context,R.color.red))}
                else if(transaction.amount>0)
                {
                    views.setBackgroundColor(ContextCompat.getColor(containerView.context,R.color.green))
                    amounts.setTextColor(ContextCompat.getColor(containerView.context,R.color.green))
                }
                items.text=transaction.name
                amounts.text=transaction.amount.toString()
            }
        }
    }
}

class DiffCallback3 : DiffUtil.ItemCallback<Expense>() {
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem == newItem
    }
}
//user detail fragment
class UserDetailFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences //making an instance of shared preferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPreferences = this.requireActivity().getSharedPreferences("data",
            Context.MODE_PRIVATE
        ) //shared preferences with file name as data
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //adding back button on toolbar
        val toolbar: MaterialToolbar =requireActivity().findViewById(R.id.detailsTopAppBar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            requireActivity().onBackPressed()
        }
        //getting the data from shared preferences entered by the user in on boarding  fragment
        getDataInsharedPreference()
        //initially disabling the edit text and button
        disableAll()
        if (!savechangesbtn.isEnabled) {
            savechangesbtn.setBackgroundColor(Color.LTGRAY)
        }
        //code to run when the pencil button is clicked in top bar
        detailsTopAppBar.setOnMenuItemClickListener { menuItem->
            when(menuItem.itemId)
            {
                R.id.editProfile ->{
                    enableAll()
                    true
                }
                else->false
            }
        }
        //setting on click listener on the save changes button in the details fragment
        savechangesbtn.setOnClickListener(){
           var flag=0
            if(TextUtils.isEmpty(UName.text))
            {
                UName.setError("Please Enter Your Name")
                UName.requestFocus()
                flag=1
            }
            else if(TextUtils.isEmpty(UBudget.text))
            {
                UBudget.setError("Please Enter Your Monthly Budget")
                UBudget.requestFocus()
                flag=1
            }
            if(flag==0)
            {
              //creating an instance of shared preference
                with(sharedPreferences.edit())
                {
                    putString("naming",UName.text.toString())
                    putString("budget",UBudget.text.toString())
                    putString("income",UIncome.text.toString())
                    putInt("id",2)
                }.apply()
                findNavController().navigate(UserDetailFragmentDirections.actionUserDetailFragmentToHomeFragment22(UName.text.toString(),2))
            }
        }

    }
//enabling all the fields
    private fun enableAll() {
        UName.isEnabled=true
        UBudget.isEnabled=true
        UIncome.isEnabled=true
        savechangesbtn.isEnabled=true
        savechangesbtn.setBackgroundColor(Color.BLUE)
    }
//disabling all the fields
    private fun disableAll() {
        UName.isEnabled = false
        UBudget.isEnabled = false
        UIncome.isEnabled = false
        savechangesbtn.isEnabled = false
    }
//sharing shared preference data to home fragment
    private fun getDataInsharedPreference() {
        val id: Int?=sharedPreferences.getInt("id",1)
        if(id==1)
        {
            val uname: String? = sharedPreferences.getString("PName", "kanhav")
            val ubudget: String? = sharedPreferences.getString("PBudget", "300")
            val uincome: String? = sharedPreferences.getString("PIncome", "500")
            uname?.let {
                UName.setText(uname)
            }
            ubudget?.let {
                UBudget.setText(ubudget)
            }
            uincome?.let {
                UIncome.setText(uincome)
            }
        }
        else //passing data to homefragment
        {
            val unames: String? = sharedPreferences.getString("naming", "kanhav")
            val ubudgets: String? = sharedPreferences.getString("budget", "300")
            val uincomes: String? = sharedPreferences.getString("income", "500")
            unames?.let {
                UName.setText(unames)
            }
            ubudgets?.let {
                UBudget.setText(ubudgets)
            }
            uincomes?.let {
                UIncome.setText(uincomes)
            }
        }

    }
}