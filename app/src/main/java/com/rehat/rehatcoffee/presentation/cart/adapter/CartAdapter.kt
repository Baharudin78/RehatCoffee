package com.rehat.rehatcoffee.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ItemCartBinding
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.presentation.common.extention.generateIDRCurrency

class CartAdapter(
    private val cart: MutableList<CartDataEntity>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    interface OnItemClickToUpdateCart {
        fun onClickUpdateCart(cart: CartDataEntity)
    }

    interface OnItemClickDeleteCart {
        fun onClickDeleteCart(cart: CartDataEntity)
    }

    fun setItemClickUpdateCart(onClickUpdateCart: OnItemClickToUpdateCart) {
        onClickListenerUpdateCart = onClickUpdateCart
    }

    fun setItemClickDeleteCart(onClickDeleteCart: OnItemClickDeleteCart) {
        onClickListenerDeleteCart = onClickDeleteCart
    }

    private var onClickListenerUpdateCart: OnItemClickToUpdateCart? = null
    private var onClickListenerDeleteCart: OnItemClickDeleteCart? = null

    fun updateCart(menu: List<CartDataEntity>) {
        cart.clear()
        cart.addAll(menu)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(cart: CartDataEntity) {
            binding.apply {
                cart.product?.images?.map {
                    Glide.with(binding.root.context)
                        .load(it?.url)
                        .placeholder(R.drawable.drink)
                        .into(ivPhoto)
                }
                tvName.text = cart.product?.productName
                tvPrice.text = generateIDRCurrency(cart.totalPrice?.toDouble() ?: 0.0)
                tvQty.text = "X ${cart.qty}"

                btnDelete.setOnClickListener {
                    onClickListenerDeleteCart?.onClickDeleteCart(cart)
                }

                btnEdit.setOnClickListener {
                    onClickListenerUpdateCart?.onClickUpdateCart(cart)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindItem(cart[position])
    }

}