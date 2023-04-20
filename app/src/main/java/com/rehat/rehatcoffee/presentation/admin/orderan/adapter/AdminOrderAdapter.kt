package com.rehat.rehatcoffee.presentation.admin.orderan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ItemAdminOrderBinding
import com.rehat.rehatcoffee.databinding.ItemSubAdminOderBinding
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminProductOrderEntity
import com.rehat.rehatcoffee.presentation.common.extention.DividerDecoration
import com.rehat.rehatcoffee.presentation.common.extention.generateIDRCurrency
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.visible


class AdminOrderAdapter(private val dataList: MutableList<AdminOrderEntity>) :
    RecyclerView.Adapter<AdminOrderAdapter.ParentViewHolder>() {

    interface OnItemClickToDone {
        fun onClickToDone(adminOrder: AdminOrderEntity)
    }

    interface OnItemClickToDonePayment {
        fun onClickToDonePayment(adminOrder: AdminOrderEntity)
    }

    fun setItemClicktoDone(onClick: OnItemClickToDone) {
        onClickListenerToDone = onClick
    }

    fun setItemClicktoDonePayment(onClick: OnItemClickToDonePayment) {
        onClickListenerToDonePayment = onClick
    }

    private var onClickListenerToDone: OnItemClickToDone? = null
    private var onClickListenerToDonePayment: OnItemClickToDonePayment? = null

    fun updateListOrder(orders: List<AdminOrderEntity>) {
        dataList.clear()
        dataList.addAll(orders)
        notifyDataSetChanged()
    }

    inner class ParentViewHolder(val binding: ItemAdminOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: AdminOrderEntity) {
            val childAdapter = ChildAdapter(order.product)
            binding.rvOrder.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = childAdapter
            }
            val dividerDecoration = DividerDecoration(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.divider_list
                )
            )
            binding.rvOrder.addItemDecoration(dividerDecoration)

            binding.btDone.setOnClickListener {
                onClickListenerToDone?.onClickToDone(order)
            }

            binding.btnDonePayment.setOnClickListener {
                onClickListenerToDonePayment?.onClickToDonePayment(order)
            }

            if (order.payStatus){
                binding.btnDonePayment.gone()
                binding.btDone.visible()
            }else{
                binding.btnDonePayment.visible()
                binding.btDone.gone()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater =
            ItemAdminOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = dataList[position]
        holder.bind(parentItem)
    }

    class ChildAdapter(val childList: List<AdminProductOrderEntity>) :
        RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

        inner class ChildViewHolder(val binding: ItemSubAdminOderBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(childItem: AdminProductOrderEntity) {
                binding.tvName.text = childItem.product?.productName
                binding.tvQty.text = childItem.qty.toString()
                binding.tvPrice.text = childItem.product?.price?.toDouble()
                    ?.let { generateIDRCurrency(it) }
                childItem.product?.images?.forEach {
                    Glide.with(binding.root.context)
                        .load(it?.url)
                        .into(binding.ivPhoto)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
            val inflater =
                ItemSubAdminOderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
