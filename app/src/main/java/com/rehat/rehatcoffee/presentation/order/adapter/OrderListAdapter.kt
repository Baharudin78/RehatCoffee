package com.rehat.rehatcoffee.presentation.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.databinding.ItemListOrderBinding
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.presentation.common.extention.generateIDRCurrency

class OrderListAdapter(
    private val orderEntity : MutableList<OrderEntity>
) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>(){

    interface OnItemClickListener {
        fun onClickListener(orderEntity: OrderEntity)
    }

    fun setItemClickListener(onClick: OnItemClickListener) {
        onClickListener = onClick
    }

    private var onClickListener: OnItemClickListener? = null

    fun updateList(order: List<OrderEntity>) {
        orderEntity.clear()
        orderEntity.addAll(order)
        notifyDataSetChanged()
    }

    inner class OrderListViewHolder(val binding : ItemListOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(orderEntity: OrderEntity){
            orderEntity.product.map { order ->
                order.product?.images?.map {image ->
                    Glide.with(binding.root.context)
                        .load(image?.url)
                        .into(binding.ivPhoto)
                }
                binding.tvPrice.text = generateIDRCurrency(orderEntity.totalPrice?.toDouble() ?: 0.0)
                binding.tvName.text = order.product?.productName
                binding.tvAlias.text = "X ${order.qty}"
                binding.root.setOnClickListener {
                    onClickListener?.onClickListener(orderEntity)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val inflater = ItemListOrderBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return OrderListViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return orderEntity.size
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bindItem(orderEntity[position])
    }

}