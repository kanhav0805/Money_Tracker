package com.example.money_tracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.money_tracker.data.TransactionListRepository
import com.example.money_tracker.data.MonthlyTransactions
//view model to get list og monthly transaction
class MonthlyTransactionViewModel(application: Application): AndroidViewModel(application) {

    private val repo: TransactionListRepository = TransactionListRepository(application)

    val month: LiveData<List<MonthlyTransactions>>
        get() = repo.getTransactionMonth()

}