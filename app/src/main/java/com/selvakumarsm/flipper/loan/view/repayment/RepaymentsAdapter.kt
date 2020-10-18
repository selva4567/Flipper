package com.selvakumarsm.flipper.loan.view.repayment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selvakumarsm.flipper.R
import com.selvakumarsm.flipper.loan.domain.model.RepaymentModel

class RepaymentsAdapter(private val repayments: MutableList<RepaymentModel> = mutableListOf(
    RepaymentModel("4,247", "12"),
    RepaymentModel("5,580", "9"),
)) : RecyclerView.Adapter<RepaymentsAdapter.RepaymentViewHolder>() {

    val backgroundIds = listOf(R.drawable.bacground_rounded_brown, R.drawable.bacground_rounded_purple)

    inner class RepaymentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            view.setBackgroundResource(backgroundIds[position])
            view.findViewById<TextView>(R.id.tvEmi).text = repayments[position].amount
            view.findViewById<TextView>(R.id.tvTenure).text = repayments[position].tenure
            if (position >= 1)
                view.findViewById<ImageView>(R.id.imageView).visibility = View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_repayment_item, parent, false)
        return RepaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepaymentViewHolder, position: Int) {
                holder.bind(position)
    }

    override fun getItemCount(): Int = repayments.size
}