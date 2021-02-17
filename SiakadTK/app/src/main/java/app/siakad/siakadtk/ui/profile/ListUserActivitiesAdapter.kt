package app.siakad.siakadtk.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class ListUserActivitiesAdapter(val listUserActivities: ArrayList<UserActivities>): RecyclerView.Adapter<ListUserActivitiesAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user_activity, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val activity = listUserActivities[position]

//        Glide.with(holder.itemView.context)
//            .load(hero.photo)
//            .apply(RequestOptions().override(55, 55))
//            .into(holder.imgPhoto)

        holder.tvName.text = activity.nama
        holder.tvDetail.text = activity.detail
        holder.tvDate.text = activity.date
    }

    override fun getItemCount(): Int {
        return listUserActivities.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_useractivity_title)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_useractivity_detail)
        var tvDate: TextView = itemView.findViewById(R.id.tv_useractivity_date)
    }
}