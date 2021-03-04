package app.siakad.siakadtk.presentation.main.profile

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.AktivitasModel
import kotlinx.android.synthetic.main.item_row_user_activity.view.*

class UserActivitiesViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertUserActivities(item: AktivitasModel) {
        with(itemView) {
            tv_useractivity_title.text = item.judul
            tv_useractivity_detail.text = item.keterangan
            tv_useractivity_date.text = item.tanggal.toString()
        }
    }
}