package app.siakad.siakadtk.ui.announcement

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.data.model.Announcement
import kotlinx.android.synthetic.main.item_row_pengumuman.view.*

class AnnouncementViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertAnnouncement(item: Announcement) {
        with(itemView) {
            tv_item_pengumuman_title.text = item.title
            tv_item_pengumuman_desc.text = item.desc
        }
    }
}