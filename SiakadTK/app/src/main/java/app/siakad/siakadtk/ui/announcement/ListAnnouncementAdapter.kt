package app.siakad.siakadtk.ui.announcement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.ui.nota.Nota
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListAnnouncementAdapter (val listAnnouncement: ArrayList<Announcement>): RecyclerView.Adapter<ListAnnouncementAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_pengumuman, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val announcement = listAnnouncement[position]

        holder.tvTitle.text = announcement.title
        holder.tvDesc.text = announcement.desc
    }

    override fun getItemCount(): Int {
        return listAnnouncement.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_item_pengumuman_title)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_item_pengumuman_desc)
    }
}