package app.siakad.siakadtkadmin.presentation.main.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.data.model.Announcement

class MainProductAdater : RecyclerView.Adapter<MainProductViewHolder>() {

    private val announcementList: ArrayList<Announcement> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return MainProductViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}