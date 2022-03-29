package com.example.money_tracker.data//package com.example.money_tracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.money_tracker.Transaction


@Database(entities = [Transaction::class],version = 4)
abstract class TransactionDataBase:RoomDatabase(){

    abstract fun getTransactionDetailDao(): TransactionDetailDao
    abstract fun getTransactionListDao(): TransactionListDao
    abstract fun monthlyTransactionDao(): MonthlyTransactionListDao
    //to make a single instance of room data base
    companion object{
        //creating a migration class to change the data base schema
        private val migration_1_2 = object : Migration(1,2)
        {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE transaction_table ADD COLUMN day INTEGER NOT NULL")
                database.execSQL("ALTER TABLE transaction_table ADD COLUMN month INTEGER NOT NULL")
                database.execSQL("ALTER TABLE transaction_table ADD COLUMN year INTEGER NOT NULL")
                database.execSQL("ALTER TABLE transaction_table ADD COLUMN monthYear LONG NOT NULL")
            }
        }
        @Volatile
        private var instance: TransactionDataBase?=null
        fun getDataBase(context: Context)= instance
            ?: synchronized(this) {

                Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDataBase::class.java,
                    "transaction_database"
                )   .fallbackToDestructiveMigration()
                    .build().also { instance =it }

            }
    }

}