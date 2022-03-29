package com.example.money_tracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.money_tracker.Transaction
import com.example.money_tracker.data.MonthlyTransactions
import com.example.money_tracker.data.TransactionListRepository
//viewmodel for home transaction list and amount in cash,credit,debit
class TransactionListViewModel (application: Application):AndroidViewModel(application)
{   private val repository: TransactionListRepository = TransactionListRepository((application))
    val myList:LiveData<List<Transaction>> = repository.getUpdatedList()
    val month: LiveData<List<MonthlyTransactions>>
        get() = repository.getMonth()
    val amount:LiveData<Float> = repository.getAmount()
    val cash:LiveData<Float> = repository.getCash()
    val credit:LiveData<Float> = repository.getCredit()
    val debit:LiveData<Float> = repository.getDebit()
}