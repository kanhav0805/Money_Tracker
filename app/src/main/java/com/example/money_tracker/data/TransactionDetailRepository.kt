package com.example.money_tracker.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.money_tracker.Transaction

class TransactionDetailRepository(context: Context)
{
    //creating an instance of dao
    private val dao= TransactionDataBase.getDataBase(context).getTransactionDetailDao()
    fun getTransaction(id:Long):LiveData<Transaction>{
        return dao.getTransaction(id)
    }
    suspend fun insert(transactions: Transaction):Long{
        return dao.insert(transactions)
    }
    suspend fun update(transaction: Transaction){
        dao.update(transaction)
    }
    suspend fun delete(transaction: Transaction)
    {
        dao.delete(transaction)
    }
}