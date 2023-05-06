package com.rehat.rehatcoffee.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rehat.rehatcoffee.databinding.ItemNotifBinding
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotificationEntity

class NotificationAdapter(
    private val notif : MutableList<NotificationEntity>
) : RecyclerView.Adapter<NotificationAdapter.NotifViewHolder>() {

    interface OnItemClickToNotif {
        fun onClickToNotif(notificationEntity: NotificationEntity)
    }

    fun setItemClicktoNotif(onClick: OnItemClickToNotif) {
        onClickListenerToNotif = onClick
    }

    private var onClickListenerToNotif: OnItemClickToNotif? = null

    fun updateNotif(notification : List<NotificationEntity>){
        notif.clear()
        notif.addAll(notification)
        notifyDataSetChanged()
    }

    inner class NotifViewHolder(val binding : ItemNotifBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(notif : NotificationEntity){
            binding.apply {
                binding.tvMessage.text = notif.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val inflater = ItemNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotifViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return notif.size
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        holder.bindItem(notif[position])
    }

}