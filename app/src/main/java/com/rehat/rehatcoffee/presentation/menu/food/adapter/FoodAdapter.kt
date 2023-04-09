package com.rehat.rehatcoffee.presentation.menu.food.adapter

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

class FoodAdapter(
    private val food: MutableList<MenuEntity>
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    interface OnItemClickToCart {
        fun onClickToCart(menuEntity: MenuEntity)
    }

    fun setItemClicktoCart(onClick: OnItemClickToCart) {
        onClickListenerToCart = onClick
    }

    private var onClickListenerToCart: OnItemClickToCart? = null

    fun updateListFood(menu: List<MenuEntity>) {
        food.clear()
        food.addAll(menu)
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return food.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindItem(food[position])
    }
}