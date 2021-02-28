package app.siakad.siakadtk.ui.announcement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.data.model.Announcement

class AnnouncementAdapter(val announcementList: ArrayList<Announcement>) : RecyclerView.Adapter<AnnouncementViewHolder>(){

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
}