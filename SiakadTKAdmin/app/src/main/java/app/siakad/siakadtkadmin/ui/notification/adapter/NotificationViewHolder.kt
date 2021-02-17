package app.siakad.siakadtkadmin.ui.notification.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.data.model.Notification
import kotlinx.android.synthetic.main.item_announcement.view.*

class NotificationViewHolder(v: View) :  RecyclerView.ViewHolder(v) {
    fun insertNotification(item: Notification) {
        with(itemView) {
            tv_item_announcement_judul.text = item.judul
            tv_item_announcement_isi.text = item.isi
            tv_item_announcement_tanggal.text = item.tanggal.toString()

            iv_item_announcement_delete.setOnClickListener {  }
        }
    }
}