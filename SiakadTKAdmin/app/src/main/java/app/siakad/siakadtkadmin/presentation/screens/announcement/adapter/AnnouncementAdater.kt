package app.siakad.siakadtkadmin.presentation.screens.announcement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman

class AnnouncementAdater : RecyclerView.Adapter<AnnouncementViewHolder>() {

    private val announcementList: ArrayList<Pengumuman> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return AnnouncementViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.insertAnnouncement(announcementList[position])
    }

    fun changeDataList(data: ArrayList<Pengumuman>) {
        if (announcementList.size > 0)
            announcementList.clear()

        announcementList.addAll(data)
        this.notifyDataSetChanged()
    }
}