package app.siakad.siakadtkadmin.presentation.screens.registration.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.presentation.screens.registration.helper.RegistrationClickHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlinx.android.synthetic.main.item_registration.view.*

class RegistrationListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
    fun insertRegistration(item: DaftarUlang) {
        with(itemView) {
            if (!item.pengguna.detailPengguna?.fotoSiswa.isNullOrEmpty()) {
                Picasso.with(v.context).load(item.pengguna.detailPengguna?.fotoSiswa).into(iv_item_registration)
            }
            tv_item_registration_nama.text = item.pengguna.nama
            tv_item_registration_kelas.text = item.pengguna.detailPengguna?.kelas
            tv_item_registration_alamat.text = item.pengguna.alamat

            ib_item_registration_detail.setOnClickListener {
                (v.context as RegistrationClickHelper).navigateToRegistrationDetail(item)
            }
        }
    }
}