package com.example.money_tracker.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.money_tracker.Transaction

class TransactionListRepository(context: Context)
{
    //creating an instance of list dao
    val listDao= TransactionDataBase.getDataBase(context).getTransactionListDao()
    fun getUpdatedList():LiveData<List<Transaction>>{
        return listDao.getUpdatedList()
    }
    fun getMonth(): LiveData<List<MonthlyTransactions>>{
        return listDao.getMonth()
    }
    fun getTransactionMonth(): LiveData<List<MonthlyTransactions>> {
        return listDao.getTransactionMonth()
    }
    fun getAmount():LiveData<Float>
    {
        return listDao.getTotalAmount()
    }
    fun getCash():LiveData<Float>
    {
        return listDao.getTotalCash()
    }
    fun getCredit():LiveData<Float>
    {
        return listDao.getTotalCredit()
    }
    fun getDebit():LiveData<Float>
    {
        return listDao.getTotalDebit()
    }

}