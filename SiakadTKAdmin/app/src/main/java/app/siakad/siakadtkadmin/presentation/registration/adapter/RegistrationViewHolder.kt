package app.siakad.siakadtkadmin.presentation.registration.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.data.model.Order
import app.siakad.siakadtkadmin.data.model.Registration
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.item_registration.view.*

class RegistrationViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun insertOrder(item: Registration) {
        with(itemView) {
            iv_item_registration
            tv_item_registration_nama.text = item.namaSiswa
            tv_item_registration_kelas.text = item.kelas
            tv_item_registration_alamat.text = item.alamat
            tv_item_registration_tanggal.text = item.tanggalRegis
        }
    }
}