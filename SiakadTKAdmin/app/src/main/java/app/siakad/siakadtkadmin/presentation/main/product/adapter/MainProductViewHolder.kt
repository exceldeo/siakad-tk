package app.siakad.siakadtkadmin.presentation.main.product.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.data.model.Announcement
import kotlinx.android.synthetic.main.item_announcement.view.*

class MainProductViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertAnnouncement(item: Announcement) {
        with(itemView) {
            tv_item_announcement_judul.text = item.judul
            tv_item_announcement_isi.text = item.isi
            tv_item_announcement_tanggal.text = item.tanggal.toString()

            iv_item_announcement_delete.setOnClickListener{}
        }
    }
}