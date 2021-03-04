package app.siakad.siakadtk.presentation.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.AktivitasModel

class UserActivitiesAdapter(val userActivitiesList: ArrayList<AktivitasModel>): RecyclerView.Adapter<UserActivitiesViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserActivitiesViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user_activity, viewGroup, false)
        return UserActivitiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserActivitiesViewHolder, position: Int) {
        val activity = userActivitiesList[position]
        holder.insertUserActivities(activity)
    }

    override fun getItemCount(): Int {
        return userActivitiesList.size
    }
}