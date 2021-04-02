package app.siakad.siakadtk.presentation.screens.announcement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pengumuman

class AnnouncementAdapter() : RecyclerView.Adapter<AnnouncementViewHolder>(){

    private val announcementList: ArrayList<Pengumuman> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AnnouncementViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_pengumuman, viewGroup, false)
        return AnnouncementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val announcement = announcementList[position]
        holder.insertAnnouncement(announcement)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    fun changeDataList(data: ArrayList<Pengumuman>) {
        if (announcementList.size > 0)
            announcementList.clear()

        announcementList.addAll(data)
        this.notifyDataSetChanged()
    }
}