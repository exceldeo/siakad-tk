package app.siakad.siakadtkadmin.presentation.screens.user.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun insertOrder(item: Siswa) {
        with(itemView) {
            iv_item_user
//            item.fotoSiswa
//            tv_item_user_judul.text = item.namaSiswa
//            tv_item_user_email.text = item.emailSiswa
//            tv_item_user_tanggal.text = item.tanggalRegisAkun
        }
    }
}