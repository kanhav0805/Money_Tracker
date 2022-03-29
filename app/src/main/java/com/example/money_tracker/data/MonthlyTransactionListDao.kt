package com.example.money_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.money_tracker.Transaction

@Dao
interface MonthlyTransactionListDao
{
    @Query("SELECT * FROM transaction_table WHERE monthYear = :monthYear ORDER BY date DESC")
    fun getTransactionByMonth(monthYear: Long): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE monthYear = :monthYear")
    fun getSumByMonth(monthYear: Long): LiveData<Float>

    @Query("SELECT name,amount,date FROM transaction_table WHERE date = :date")
    fun getAmountByMonth(date: String): LiveData<List<Expense>>
}