package app.siakad.siakadtkadmin.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.NotifikasiModel

class NotificationAdapter() : RecyclerView.Adapter<NotificationViewHolder>() {

    private val notificationList: ArrayList<NotifikasiModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return NotificationViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}