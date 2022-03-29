package com.example.money_tracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.money_tracker.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.transaction_list_item.*

class TransactionAdapter (private val listener:(Long)->Unit):ListAdapter<Transaction, TransactionAdapter.ViewHolder>(
    DiffCallback()
)
{   //creating an empty list of type mutable
    val myList:MutableList<Transaction> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inputLayout=LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_item,parent,false)
        return ViewHolder(inputLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           holder.bind(getItem(position))
    }

    inner class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init{
            itemView.setOnClickListener{
                listener.invoke(getItem(adapterPosition).id)
            }
        }
        //function to bind entered transaction in the list view
        fun bind(transaction: Transaction){
            //setting title of transaction
            title_cv.text=transaction.title
            date_cv.text=transaction.date
            amount_tv.text=transaction.amount.toString()
            //changing icon when tag is changed
            when(transaction.tag)
           {
               Tag.Entertainment.ordinal->{imagez.setImageResource(R.drawable.ic_entertainment)}
               Tag.Food.ordinal->{imagez.setImageResource(R.drawable.ic_food)}
               Tag.Transportation.ordinal->{imagez.setImageResource(R.drawable.ic_bus_logo)}
               Tag.Medical.ordinal->{imagez.setImageResource(R.drawable.ic_medicine)}
               Tag.Loan.ordinal->{imagez.setImageResource(R.drawable.ic_loan)}
               Tag.Other.ordinal->{imagez.setImageResource(R.drawable.ic_other)}
           }
            //changing the transaction type text view
            when(transaction.transactionType)
            {
                TransactionType.Cash.ordinal->{
                    val ans1:String="Cash"
                    type_cv.text=ans1
                }
                TransactionType.Credit.ordinal->{
                    val ans2:String="Credit Card"
                    type_cv.text=ans2
                }
                TransactionType.Debit.ordinal->{
                    val ans3:String = "Debit Card"
                    type_cv.text=ans3
                }
            }
            when(transaction.income_expense)
            {
                IncomeExpense.Expense.ordinal->{
                    cardv.setBackgroundResource(R.color.red)
                    amount_tv.setTextColor(ContextCompat.getColor(containerView.context,
                        R.color.red
                    ))
                }
                IncomeExpense.Income.ordinal->{
                    cardv.setBackgroundResource(R.color.green)
                    amount_tv.setTextColor(ContextCompat.getColor(containerView.context,
                        R.color.green
                    ))
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }


}