package app.siakad.siakadtk.presentation.announcement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PengumumanModel

class AnnouncementAdapter(val announcementList: ArrayList<PengumumanModel>) : RecyclerView.Adapter<AnnouncementViewHolder>(){

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