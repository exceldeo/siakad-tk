package app.siakad.siakadtkadmin.presentation.screens.classroom.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.Kelas
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import kotlinx.android.synthetic.main.item_announcement.view.*
import kotlinx.android.synthetic.main.item_classroom.view.*

class ClassroomListViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertAnnouncement(item: Kelas) {
        with(itemView) {
            tv_item_classroom_judul
            tv_item_classroom_kelas

            iv_item_classroom_detail.setOnClickListener{}
        }
    }
}