package app.siakad.siakadtkadmin.ui.announcement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.data.model.Announcement

class AnnouncementAdater : RecyclerView.Adapter<AnnouncementViewHolder>() {

    private val announcementList: ArrayList<Announcement> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return AnnouncementViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}