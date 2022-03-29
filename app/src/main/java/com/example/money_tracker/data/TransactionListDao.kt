package com.example.money_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.money_tracker.Transaction

@Dao
interface TransactionListDao {
    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getUpdatedList():LiveData<List<Transaction>>
    @Query("SELECT t1.monthYear, t1.month, t1.year, SUM(t1.amount) as sum,(SELECT t2.name FROM transaction_table as t2 WHERE t1.monthYear = t2.monthYear LIMIT 3) as expense FROM transaction_table as t1 GROUP BY monthYear ORDER BY year, month")
    fun getMonth(): LiveData<List<MonthlyTransactions>>
    @Query("SELECT t1.monthYear, t1.month, t1.year, SUM(t1.amount) as sum,(SELECT t2.name FROM transaction_table as t2 WHERE t1.monthYear = t2.monthYear LIMIT 3) as expense FROM transaction_table as t1 GROUP BY monthYear ORDER BY year, month")
    fun getTransactionMonth(): LiveData<List<MonthlyTransactions>>
    @Query("SELECT Sum(amount) FROM transaction_table")
    fun getTotalAmount():LiveData<Float>
    @Query("SELECT Sum(amount) FROM transaction_table WHERE transactionType=0")
    fun getTotalCash():LiveData<Float>
    @Query("SELECT Sum(amount) FROM transaction_table WHERE transactionType=1")
    fun getTotalCredit():LiveData<Float>
    @Query("SELECT Sum(amount) FROM transaction_table WHERE transactionType=2")
    fun getTotalDebit():LiveData<Float>
}