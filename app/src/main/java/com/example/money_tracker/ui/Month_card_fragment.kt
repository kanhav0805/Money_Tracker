package com.example.money_tracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_tracker.MonthlyCardAdapter
import com.example.money_tracker.R
import kotlinx.android.synthetic.main.fragment_month_card_fragment.*


class month_card_fragment : Fragment() {

    private lateinit var viewModel: MonthlyTransactionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(MonthlyTransactionViewModel::class.java)
        return inflater.inflate(R.layout.fragment_month_card_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        monthListToolBar.setNavigationOnClickListener(){
            requireActivity().onBackPressed()
        }
        // Month Card List
        with(monthBudgetRv) {
            layoutManager = LinearLayoutManager(activity)
            adapter = MonthlyCardAdapter({
              findNavController().navigate(month_card_fragmentDirections.actionMonthCardFragmentToMontlyDetailFragment(it))
            },requireContext())
        }



        viewModel.month.observe(viewLifecycleOwner, Observer {
            (monthBudgetRv.adapter as MonthlyCardAdapter).submitList(it)
        })
    }
}

