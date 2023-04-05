package com.rehat.rehatcoffee.presentation.menu.drink.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rehat.rehatcoffee.databinding.ItemMenuBinding
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.visible

class DrinkAdapter(
    private val drink: MutableList<MenuEntity>
) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    var isClicked = false
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
        @SuppressLint("SetTextI18n")
        fun bindItem(menu : MenuEntity){
            var qtyValue = 1
            binding.apply {
//                Glide.with(binding.root.context)
//                    .load(menu.imagesEntity)
//                    .into(ivPhoto)
                tvName.text = menu.productName
                tvAlias.text = menu.description
                tvPrice.text = menu.price.toString()

                btnAddToCart.setOnClickListener {
                    onClickListener?.onClick(menu)
                    layout.visible()
                    btnAddToCart.gone()
                }
                btnMinus.setOnClickListener {
                    if (tvQty.text.toString().toInt() <= 0){
                        btnAddToCart.visible()
                        layout.gone()
                    }else{
                        tvQty.text = (qtyValue.toString().toInt() -1).toString()
                    }
                }

                btnPlus.setOnClickListener {
                    tvQty.text = (qtyValue.toString().toInt() +1).toString()
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