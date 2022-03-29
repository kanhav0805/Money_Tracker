package com.example.money_tracker.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_tracker.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.myMonthsTransactionList
import kotlinx.android.synthetic.main.fragment_montly_detail.*


class MontlyDetailFragment : Fragment() {
   lateinit var viewModel: MonthlyTransactionListViewModel
   lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel=ViewModelProvider(this).get(MonthlyTransactionListViewModel::class.java)
        sharedPreferences=this.requireActivity().getSharedPreferences("data",Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_montly_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: android.os.Bundle?) {
        super.onActivityCreated(savedInstanceState)
          val ans:String?=sharedPreferences.getString("naming","kanhav")
          val budget:String?=sharedPreferences.getString("budget","1000")
          monthAmount.text=budget
         monthToolbar.setNavigationOnClickListener()
         {requireActivity().onBackPressed()}
     //setting on click listener on bottom navigation view
        bottomNavigationView2.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.floatingBtn->{findNavController().navigate(MontlyDetailFragmentDirections.actionMontlyDetailFragmentToAddTransactionFragment(0))
                    true}
                R.id.bottom_calendar->{findNavController().navigate(MontlyDetailFragmentDirections.actionMontlyDetailFragmentToCalendarFragment2())
                    true
                }
                R.id.monthlyExpenses->{findNavController().navigate(MontlyDetailFragmentDirections.actionMontlyDetailFragmentToMonthCardFragment())
                    true
                }
                else->false
            }
        }
      //filling the recycler view with monthly transactions
        with(myMonthsTransactionList)
        {
            layoutManager=LinearLayoutManager(activity)
            adapter= TransactionAdapter{
               findNavController().navigate(MontlyDetailFragmentDirections.actionMontlyDetailFragmentToAddTransactionFragment(it))
            }
            setHasFixedSize(true)
        }
        val id=MontlyDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setMonthYear(id)
        viewModel.transactionByMonth.observe(viewLifecycleOwner, Observer{
            (myMonthsTransactionList.adapter as TransactionAdapter).submitList(it)
        })
    }


}