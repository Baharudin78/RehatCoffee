package com.rehat.rehatcoffee.presentation.menu.drink.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.databinding.ItemMenuBinding
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity

class DrinkAdapter(
    private val drink: MutableList<MenuEntity>
) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    interface OnItemClick {
        fun onClick(menuEntity: MenuEntity)
    }

    fun setItemClickListener(onClick: OnItemClick) {
        onClickListener = onClick
    }

    private var onClickListener: OnItemClick? = null

    fun updateListDrink(menu: List<MenuEntity>) {
        drink.clear()
        drink.addAll(menu)
        notifyDataSetChanged()
    }

    inner class DrinkViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(menu : MenuEntity){
            binding.apply {
                Glide.with(binding.root.context)
                    .load(menu.imagesEntity)
                    .into(ivPhoto)
                tvName.text = menu.productName
                tvAlias.text = menu.description
                tvPrice.text = menu.price.toString()

                btnAddToCart.setOnClickListener {
                    onClickListener?.onClick(menu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val inflater = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return DrinkViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return drink.size
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bindItem(drink[position])
    }
}