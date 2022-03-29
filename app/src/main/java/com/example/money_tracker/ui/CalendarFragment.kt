package com.example.money_tracker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_tracker.CalendarAdapter
import com.example.money_tracker.R
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.DateFormat
import java.util.*


class CalendarFragment : Fragment() {
    private lateinit var viewModel: MonthlyTransactionListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MonthlyTransactionListViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CalendarToolBar.setNavigationOnClickListener(){
            requireActivity().onBackPressed()
        }
        // get a calendar instance
        val calendar = Calendar.getInstance()

        myCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // set the calendar date as calendar view selected date
            calendar.set(year, month, dayOfMonth)
            var m = month+1

            var date:String = "$dayOfMonth/$m/$year"
            if(dayOfMonth<10 && month<10)
            {date="0$dayOfMonth/0$m/$year"}
            else if(dayOfMonth>10 && month<10)
                date = "$dayOfMonth/0$m/$year"
            else
            {
                date="$dayOfMonth/$m/$year"
            }
            viewModel.setDate(date)

            Log.d("TAG", "yearmonth:$date")

            // format the calendar selected date
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            val formattedDate = dateFormatter.format(calendar.time)

            // show calendar view selected date on text view
            // medium format date
            calendarDate.text = formattedDate

            // Show the corresponding transaction on screen
            with(dailyTransactionRv){
                layoutManager = LinearLayoutManager(activity)
                adapter = CalendarAdapter {

                }

            }
           //view model to get daily transaction
            viewModel.getAmount(date).observe(viewLifecycleOwner, androidx.lifecycle.Observer{
                (dailyTransactionRv.adapter as CalendarAdapter).submitList(it)
            })



        }

    }
    }

