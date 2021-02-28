package app.siakad.siakadtk.ui.profile

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.data.model.UserActivities
import kotlinx.android.synthetic.main.item_row_user_activity.view.*

class UserActivitiesViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertUserActivities(item: UserActivities) {
        with(itemView) {
            tv_useractivity_title.text = item.title
            tv_useractivity_detail.text = item.desc
            tv_useractivity_date.text = item.date.toString()
        }
    }
}