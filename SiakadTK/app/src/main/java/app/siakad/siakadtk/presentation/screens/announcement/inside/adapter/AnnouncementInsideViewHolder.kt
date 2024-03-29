package app.siakad.siakadtk.presentation.screens.announcement.inside.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import kotlinx.android.synthetic.main.item_row_pengumuman_inside.view.*

class AnnouncementInsideViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertAnnouncement(item: PengumumanModel) {
        with(itemView) {
            tv_item_pengumumanin_title.text = item.judul
            tv_item_pengumumanin_desc.text = item.keterangan
            tv_item_pengumumanin_date.text = item.tanggal
            tv_item_pengumumanin_tujuan.text = "Kepada : " + item.tipe.toUpperCase()

            if(!item.confirmableState && item.confirmable ) {
                tv_item_pengumumanin_confirmable.visibility =  View.VISIBLE
            }
        }
    }
}