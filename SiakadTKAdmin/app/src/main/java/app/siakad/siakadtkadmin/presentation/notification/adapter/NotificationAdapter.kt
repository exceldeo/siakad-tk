package app.siakad.siakadtkadmin.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.NotifikasiModel
import app.siakad.siakadtkadmin.infrastructure.data.Notifikasi
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman

class NotificationAdapter() : RecyclerView.Adapter<NotificationViewHolder>() {

    private val notificationList: ArrayList<Notifikasi> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return NotificationViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.insertNotification(notificationList[position])
    }

    fun changeDataList(data: ArrayList<Notifikasi>) {
        if (notificationList.size > 0)
            notificationList.clear()

        notificationList.addAll(data)
        this.notifyDataSetChanged()
    }
}