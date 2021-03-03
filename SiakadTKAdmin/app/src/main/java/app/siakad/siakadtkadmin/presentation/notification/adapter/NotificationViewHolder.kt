package app.siakad.siakadtkadmin.presentation.notification.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.Notifikasi
import kotlinx.android.synthetic.main.item_announcement.view.*

class NotificationViewHolder(v: View) :  RecyclerView.ViewHolder(v) {
    fun insertNotification(item: Notifikasi) {
        with(itemView) {
            tv_item_announcement_judul.text = item.judul
            tv_item_announcement_isi.text = item.keterangan
            tv_item_announcement_tanggal.text = item.tanggal

            iv_item_announcement_delete.setOnClickListener {  }
        }
    }
}