package com.rehat.rehatcoffee.presentation.admin.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ItemProductBinding
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.presentation.common.extention.generateIDRCurrency

class ProductAdapter(
    private val product: MutableList<ProductEntity>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnItemClickToUpdate {
        fun onClickToUpdate(productEntity: ProductEntity)
    }

    interface OnItemClickToDelete {
        fun onClickToDelete(productEntity: ProductEntity)
    }

    fun setItemClicktoUpdate(onClick: OnItemClickToUpdate) {
        onClickListenerToUpdate = onClick
    }

    fun setItemClicktoDelete(onClick: OnItemClickToDelete) {
        onClickListenerToDelete = onClick
    }

    private var onClickListenerToUpdate: OnItemClickToUpdate? = null
    private var onClickListenerToDelete: OnItemClickToDelete? = null

    fun updateListFood(products: List<ProductEntity>) {
        product.clear()
        product.addAll(products)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(product : ProductEntity) {
            binding.apply {
                product.images.map {
                    Glide.with(binding.root.context)
                        .load(it?.url)
                        .placeholder(R.drawable.drink)
                        .into(ivPhoto)
                }
                tvName.text = product.productName
                tvAlias.text = product.id
                tvPrice.text = generateIDRCurrency(product.price?.toDouble() ?: 0.0)

                btnEdit.setOnClickListener {
                    onClickListenerToUpdate?.onClickToUpdate(product)
                }

                btnDelete.setOnClickListener {
                    onClickListenerToDelete?.onClickToDelete(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindItem(product[position])
    }
}