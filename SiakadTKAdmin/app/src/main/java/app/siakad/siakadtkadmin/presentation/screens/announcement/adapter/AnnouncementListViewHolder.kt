package app.siakad.siakadtkadmin.presentation.screens.announcement.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.presentation.screens.announcement.listener.AnnouncementEditListener
import kotlinx.android.synthetic.main.item_announcement.view.*

class AnnouncementListViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
    fun insertAnnouncement(item: PengumumanModel) {
        with(itemView) {
            tv_item_announcement_judul.text = item.judul
            tv_item_announcement_isi.text = item.keterangan
            tv_item_announcement_tanggal.text = item.tanggal

            iv_item_announcement_delete.setOnClickListener{
                (v.context as AnnouncementEditListener).navigateToAnnouncementEdit(item)
            }
        }
    }
}