package com.example.money_tracker.ui
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_tracker.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.eazegraph.lib.models.PieModel

class HomeFragment : Fragment() {
    lateinit var viewModel: TransactionListViewModel
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(TransactionListViewModel::class.java)
        sharedPreferences =
            this.requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting the user name and monthly budget in the home fragment
        val ids: Int = HomeFragmentArgs.fromBundle(requireArguments()).ids
        if (ids == 1) {
            val userName: String = HomeFragmentArgs.fromBundle(requireArguments()).userName
            val str: String = "Hi!! $userName"
            hiTvHome.text = str
            val monthly_budget: Float = sharedPreferences.getString("PBudget", "10000")!!.toFloat()
            yearlyAmounttv.text = (monthly_budget * 12).toString()
        } else if (ids == 2) {
            val userNames: String = HomeFragmentArgs.fromBundle(requireArguments()).userName
            val str: String = "Hi!! $userNames"
            hiTvHome.text = str
            val monthly_budget: Float = sharedPreferences.getString("budget", "200")!!.toFloat()
            yearlyAmounttv.text = (monthly_budget * 12).toString()
        }
        val sharedPreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        //calling function to set the cash,credit,debit amount after every tramnsaction
        setTextUsingViewModel()
        //update pie chart
      updateMyPieChart()
        //setting on click listener on the top bar in home fragment
        homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.homePerson -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToUserDetailFragment())
                    true
                }
                else -> false
            }
        }
        //setting on click listener on the bottom navigation view
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_calendar -> {
                    //code to go to daily transaction fragment
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToCalendarFragment2())
                    true
                }
                R.id.floatingBtn -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragment2ToAddTransactionFragment(
                            0
                        )
                    )
                    true
                }
                R.id.monthlyExpenses -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToMonthCardFragment())
                    true
                }
                else -> false
            }
        }
        //setting up the recycler view in home fragment
        with(myMonthsTransactionList)
        {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = TransactionAdapter {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragment2ToAddTransactionFragment(
                        it
                    )
                )//pass it here
            }
            setHasFixedSize(true)
        }
        //observing view model to get live data of list of transaction
        viewModel.myList.observe(viewLifecycleOwner, Observer {
            (myMonthsTransactionList.adapter as TransactionAdapter).submitList(it)
        })
    }
//function to get live data of cash,credit and debit amount
    private fun setTextUsingViewModel() {
//        viewModel.amount.observe(viewLifecycleOwner, Observer {
//            amount_tv.text=it.toString()
//        })
        viewModel.cash.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cashAmountTv.text = it.toString()
                 updateMyPieChart()
            } else {
                cashAmountTv.setText("0.0")
            }
        })
        viewModel.credit.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                creditBalnace.text = it.toString()
                  updateMyPieChart()
            } else {
                creditBalnace.setText("0.0")
            }
        })
        viewModel.debit.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                debitCardBalnace.text = it.toString()
                updateMyPieChart()
            } else {
                debitCardBalnace.setText("0.0")
            }
           // updateMyPieChart()
        })
    }

//function to update pie chart
    private fun updateMyPieChart() {
    //getting the value from text views in float
        val mcash=cashAmountTv.text.toString().toFloat()
        val mcredit=creditBalnace.text.toString().toFloat()
        val mdebit=debitCardBalnace.text.toString().toFloat()
    val cash:Float = (mcash / (mcash+mdebit+mcredit))*100
    val credit:Float = (mcredit / (mcash+mdebit+mcredit))*100
    val debit:Float= (mdebit / (mcash+mdebit+mcredit))*100

        piechart.clearChart()
        // Adding pie chart
        piechart?.addPieSlice(
            PieModel("Cash", cash, Color.parseColor("#C2185B"))
        )
        piechart?.addPieSlice(
            PieModel("Credit", credit, Color.parseColor("#FDD835"))
        )
        piechart?.addPieSlice(
            PieModel("debit", debit, Color.parseColor("#58ED19"))
        )

        piechart?.startAnimation();

    }


    }
