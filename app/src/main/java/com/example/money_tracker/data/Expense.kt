package com.example.money_tracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Expense(
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "date")
    val date:String,
    @ColumnInfo(name = "amount")
    val amount:Float

)