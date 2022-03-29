package com.example.money_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.money_tracker.Transaction

// dao to insert,update,delete transaction in the data base
@Dao
interface TransactionDetailDao
{   @Query("SELECT * FROM TRANSACTION_TABLE WHERE `id` = :id")
    fun getTransaction(id:Long):LiveData<Transaction>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction):Long
    @Update
    suspend fun update(transaction: Transaction)
    @Delete
    suspend fun delete(transaction: Transaction)
}