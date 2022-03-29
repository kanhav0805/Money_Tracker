package com.example.money_tracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.money_tracker.Transaction
import com.example.money_tracker.data.Expense
import com.example.money_tracker.data.MonthlyTransactionListRepository

class MonthlyTransactionListViewModel(application: Application): AndroidViewModel(application) {
    private val repo: MonthlyTransactionListRepository = MonthlyTransactionListRepository(application)

    private val _transactionMonthYear = MutableLiveData<Long>(0)
    private val _date = MutableLiveData<String>()

    val transactionByMonth: LiveData<List<Transaction>> = Transformations.switchMap(_transactionMonthYear){ id ->
        repo.getTransactionByMonth(id)
    }

    fun getAmount(date: String): LiveData<List<Expense>>{
        return repo.getAmountByMonth(date)
    }
    fun setMonthYear(monthYear: Long){
        _transactionMonthYear.value = monthYear
    }
    fun setDate(date: String){
        _date.value = date
    }

}