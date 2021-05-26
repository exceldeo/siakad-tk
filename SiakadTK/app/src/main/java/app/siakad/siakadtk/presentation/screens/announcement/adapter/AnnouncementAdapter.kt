package app.siakad.siakadtk.presentation.screens.announcement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.presentation.screens.announcement.inside.adapter.AnnouncementInsideAdapter

class AnnouncementAdapter() : RecyclerView.Adapter<AnnouncementViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val announcementList: ArrayList<PengumumanModel> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AnnouncementViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_pengumuman, viewGroup, false)
        return AnnouncementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val announcement = announcementList[position]
        holder.insertAnnouncement(announcement)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(announcementList[holder.adapterPosition]) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PengumumanModel)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    fun changeDataList(data: ArrayList<PengumumanModel>) {
        if (announcementList.size > 0)
            announcementList.clear()

        announcementList.addAll(data)
        this.notifyDataSetChanged()
    }
}