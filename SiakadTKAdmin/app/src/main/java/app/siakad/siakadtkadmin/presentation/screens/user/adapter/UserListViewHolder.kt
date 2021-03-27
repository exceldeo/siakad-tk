package app.siakad.siakadtkadmin.presentation.screens.user.detail.verified.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import kotlinx.android.synthetic.main.item_user.view.*

class UserListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun insertUser(item: Siswa) {
        with(itemView) {
            tv_item_user_judul.text = item.nama
            tv_item_user_email.text = item.email
            tv_item_user_kelas.text
        }
    }
}