

package com.example.money_tracker.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.money_tracker.Transaction

class MonthlyTransactionListRepository(context: Application) {
    private val monthlyTransactionListDao: MonthlyTransactionListDao = TransactionDataBase.getDataBase(context).monthlyTransactionDao()

    fun getTransactionByMonth(monthYear: Long): LiveData<List<Transaction>> {
        return monthlyTransactionListDao.getTransactionByMonth(monthYear)
    }
    fun getSumByMonth(monthYear: Long): LiveData<Float> {
        return monthlyTransactionListDao.getSumByMonth(monthYear)
    }
    fun getAmountByMonth(date: String) : LiveData<List<Expense>> {
        return monthlyTransactionListDao.getAmountByMonth(date)
    }
}