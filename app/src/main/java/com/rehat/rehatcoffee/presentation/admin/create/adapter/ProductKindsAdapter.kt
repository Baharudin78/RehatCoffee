package com.rehat.rehatcoffee.presentation.admin.create.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ItemRoleBinding
import com.rehat.rehatcoffee.domain.product.entity.KindsEntity
import com.rehat.rehatcoffee.presentation.common.extention.withDelay

class ProductKindsAdapter(
    private val productEntity : List<KindsEntity>
) : RecyclerView.Adapter<ProductKindsAdapter.LetterKindHolder>(){

    private var selectedPosition = -1

    interface OnItemClickListener{
        fun onClick(kinds: KindsEntity)
    }

    fun setItemClickListener(clickInterface : OnItemClickListener) {
        onClickListener = clickInterface
    }

    private var onClickListener : OnItemClickListener? = null

    inner class LetterKindHolder(
        val binding : ItemRoleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun binItem(item : KindsEntity){
            binding.apply {
                binding.tvRole.text = item.name
            }
            if (item.selected){
                binding.layoutCard.background =
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.bg_selector
                    )
                binding.tvRole.setTextColor(Color.WHITE)
            }
            binding.root.setOnClickListener {
                onClickListener?.onClick(item)
                selectItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterKindHolder {
        val inflater = ItemRoleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterKindHolder(inflater)
    }

    override fun onBindViewHolder(holder: LetterKindHolder, position: Int) {
        holder.binItem(productEntity[position])
    }

    override fun getItemCount(): Int {
        return productEntity.size
    }

    private fun selectItem(position: Int) {
        if(position != selectedPosition) {
            if(selectedPosition > -1) {
                productEntity[selectedPosition].selected = false
                notifyItemChanged(selectedPosition)
            }

            selectedPosition = position
            productEntity[position].selected = true

            withDelay {
                notifyItemChanged(position)
            }
        }
    }
}