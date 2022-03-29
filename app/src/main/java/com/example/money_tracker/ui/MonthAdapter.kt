package com.example.money_tracker.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.money_tracker.R
import com.example.money_tracker.data.Expense
import kotlinx.android.synthetic.main.month_card_rv_item.view.*

class MonthAdapter(private val children: List<Expense>) :
    RecyclerView.Adapter<MonthAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.month_card_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }
   //bind function to bind layout ans text
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val child = children[position]
        holder.itemName.text = child.name
        if (child.amount < 0) {
            holder.itemAmount.text = child.amount.toString()
            holder.itemAmount.setTextColor(Color.RED)
        } else {
            holder.itemAmount.text = "+" + child.amount.toString()
            holder.itemAmount.setTextColor(Color.GREEN)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemName: TextView = itemView.titletv
        val itemAmount: TextView = itemView.pricetv
    }
}