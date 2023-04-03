package com.rehat.rehatcoffee.presentation.register.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ItemRoleBinding
import com.rehat.rehatcoffee.domain.register.entity.RoleEntity
import com.rehat.rehatcoffee.presentation.common.extention.withDelay

class RoleAdapter(
    private val roleEntity: List<RoleEntity>
) : RecyclerView.Adapter<RoleAdapter.RoleViewHolder>() {

    private var selectedPosition = -1

    interface OnItemClickListener {
        fun onClick(roleEntity: RoleEntity)
    }

    fun setItemClickListener(clickInterface: OnItemClickListener) {
        onClickListener = clickInterface
    }

    private var onClickListener: OnItemClickListener? = null

    inner class RoleViewHolder(
        val binding: ItemRoleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: RoleEntity) {
            binding.apply {
                binding.tvRole.text = item.name
            }
            if (item.isSelected) {
                binding.layoutCard.background =
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.bg_selector
                    )
                binding.tvRole.setTextColor(Color.WHITE)
            }
            binding.layoutCard.setOnClickListener {
                onClickListener?.onClick(item)
                selectItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoleViewHolder {
        val inflater = ItemRoleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoleViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RoleAdapter.RoleViewHolder, position: Int) {
        holder.bindItem(roleEntity[position])
    }

    override fun getItemCount(): Int {
        return roleEntity.size
    }

    private fun selectItem(position: Int) {
        if (position == selectedPosition) {
            return
        }
        if (selectedPosition >= 0 && selectedPosition < roleEntity.size) {
            roleEntity[selectedPosition].isSelected = false
            notifyItemChanged(selectedPosition)
        }

        selectedPosition = position
        roleEntity[selectedPosition].isSelected = true
        withDelay {
            notifyItemChanged(position)
        }
    }

}