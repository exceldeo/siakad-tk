package app.siakad.siakadtkadmin.presentation.screens.announcement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PengumumanModel

class AnnouncementListAdater : RecyclerView.Adapter<AnnouncementListViewHolder>() {

    private val announcementList: ArrayList<PengumumanModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementListViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return AnnouncementListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    override fun onBindViewHolder(holder: AnnouncementListViewHolder, position: Int) {
        holder.insertAnnouncement(announcementList[position])
    }

    fun changeDataList(data: ArrayList<PengumumanModel>) {
        if (announcementList.size > 0)
            announcementList.clear()

        announcementList.addAll(data)
        this.notifyDataSetChanged()
    }
}