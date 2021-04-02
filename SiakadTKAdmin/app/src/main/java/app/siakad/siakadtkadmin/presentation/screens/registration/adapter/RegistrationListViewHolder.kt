package app.siakad.siakadtkadmin.presentation.screens.registration.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import kotlinx.android.synthetic.main.item_registration.view.*

class RegistrationListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun insertRegistration(item: DaftarUlang) {
        with(itemView) {
            iv_item_registration
            tv_item_registration_nama.text = item.namaSiswa
            tv_item_registration_kelas.text = item.kelas
            tv_item_registration_alamat.text = item.alamat
        }
    }
}