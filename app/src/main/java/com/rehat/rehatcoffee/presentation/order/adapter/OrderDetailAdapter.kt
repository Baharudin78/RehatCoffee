package com.rehat.rehatcoffee.presentation.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ItemDetailOrderBinding
import com.rehat.rehatcoffee.domain.order.entity.ProductOrderListEntity
import com.rehat.rehatcoffee.presentation.common.extention.generateIDRCurrency
import java.text.NumberFormat
import java.util.*

class OrderDetailAdapter(
    private val orderList: MutableList<ProductOrderListEntity>
) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    fun updateOrder(order: List<ProductOrderListEntity>) {
        orderList.clear()
        orderList.addAll(order)
        notifyDataSetChanged()
    }

    inner class OrderDetailViewHolder(val binding: ItemDetailOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(order: ProductOrderListEntity) {
            binding.apply {
                order.product?.images?.map {
                    Glide.with(binding.root.context)
                        .load(it?.url)
                        .placeholder(R.drawable.drink)
                        .into(ivPhoto)
                }
                tvName.text = order.product?.productName
                tvAlias.text = "X ${order.qty}"
                tvPrice.text = generateIDRCurrency(order.totalPrice?.toDouble() ?: 0.0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val inflater = ItemDetailOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bindItem(orderList[position])
    }

}