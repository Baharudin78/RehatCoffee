package com.rehat.rehatcoffee.presentation.admin.orderan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rehat.rehatcoffee.databinding.ItemAdminOrderBinding
import com.rehat.rehatcoffee.databinding.ItemSubAdminOderBinding
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminProductOrderEntity
import com.rehat.rehatcoffee.presentation.common.extention.generateIDRCurrency
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.visible

class AdminOrderAdapter(private val dataList: MutableList<AdminOrderEntity>) : RecyclerView.Adapter<AdminOrderAdapter.ParentViewHolder>() {

    interface OnItemClickToDone {
        fun onClickToDone(adminOrder: AdminOrderEntity)
    }

    fun setItemClicktoDone(onClick: OnItemClickToDone) {
        onClickListenerToDone = onClick
    }

    private var onClickListenerToDone: OnItemClickToDone? = null

    fun updateListOrder(orders: List<AdminOrderEntity>) {
        dataList.clear()
        dataList.addAll(orders)
        notifyDataSetChanged()
    }

    inner class ParentViewHolder(val binding : ItemAdminOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order : AdminOrderEntity) {
            val childAdapter = ChildAdapter(order.product)
            binding.rvOrder.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = childAdapter
            }
            binding.btDone.setOnClickListener {
                onClickListenerToDone?.onClickToDone(order)
                if (order.orderStatus) {
                    binding.tvDone.isVisible = true
                    binding.btDone.isVisible = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = ItemAdminOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = dataList[position]
        holder.bind(parentItem)
    }


    class ChildAdapter(val childList: List<AdminProductOrderEntity>) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

        inner class ChildViewHolder(val binding : ItemSubAdminOderBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(childItem: AdminProductOrderEntity) {
                binding.tvNama.text = childItem.product?.productName
                binding.tvQty.text = childItem.qty.toString()
                binding.tvPrice.text = childItem.product?.price?.toDouble()
                    ?.let { generateIDRCurrency(it) }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
            val inflater = ItemSubAdminOderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ChildViewHolder(inflater)
        }

        override fun getItemCount(): Int {
            return childList.size
        }

        override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
            val childItem = childList[position]
            holder.bind(childItem)
        }
    }
}
