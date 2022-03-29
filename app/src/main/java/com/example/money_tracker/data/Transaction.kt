package com.example.money_tracker

import android.widget.DatePicker
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


enum class Tag{
    Entertainment,Transportation,Loan,Medical,Food,Other
}
enum class TransactionType{
    Cash,Credit,Debit
}
enum class IncomeExpense{
    Income,Expense
}
//making an entity to store and update transaction in room db
@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0, //to uniquely identify the item
    @ColumnInfo(name = "name")
    val title:String, //transaction title
    val amount:Float,//transaction amount
    val date:String,//date in format year/month/day
    val day:Int,//to get the day of transaction
    val month:Int,//to get the month of transaction
    val year:Int,//to get the year of transaction
    val monthYear:Long,//month year of transaction
    val transactionType:Int,//cash-0,credit-1,debit-2
    //val datePicker: Date,//to get the date of the user transaction
    val tag:Int,//Entertainment-0,Transportation-1,Loan-2,Medical-3,Food-4
    val comments:String, //to store the note entered by the user
    val fromDate:String,//from date of recurring transaction
    val toDate:String,//to date of recurring transaction
    val income_expense:Int//Income-0,Expense-1
)