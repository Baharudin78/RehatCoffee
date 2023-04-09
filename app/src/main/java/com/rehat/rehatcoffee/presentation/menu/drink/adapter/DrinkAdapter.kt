package com.rehat.rehatcoffee.presentation.menu.drink.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ItemMenuBinding
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.presentation.common.extention.generateIDRCurrency
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.visible

class DrinkAdapter(
    private val drink: MutableList<MenuEntity>
) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    interface OnItemClickToCart {
        fun onClickToCart(menuEntity: MenuEntity)
    }

    fun setItemClicktoCart(onClick: OnItemClickToCart) {
        onClickListenerToCart = onClick
    }

    private var onClickListenerToCart: OnItemClickToCart? = null

    fun updateListDrink(menu: List<MenuEntity>) {
        drink.clear()
        drink.addAll(menu)
        notifyDataSetChanged()
    }

    inner class DrinkViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(menu: MenuEntity) {
            binding.apply {
                menu.imagesEntity.map {
                    Glide.with(binding.root.context)
                        .load(it?.url)
                        .placeholder(R.drawable.drink)
                        .into(ivPhoto)
                }
                tvName.text = menu.productName
                tvAlias.text = menu.description
                tvPrice.text = generateIDRCurrency(menu.price?.toDouble() ?: 0.0)

                btnAddToCart.setOnClickListener {
                    onClickListenerToCart?.onClickToCart(menu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val inflater = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return drink.size
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bindItem(drink[position])
    }
}