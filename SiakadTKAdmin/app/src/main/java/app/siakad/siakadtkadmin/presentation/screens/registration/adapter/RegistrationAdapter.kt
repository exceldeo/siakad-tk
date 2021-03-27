package app.siakad.siakadtkadmin.presentation.screens.registration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang

class RegistrationAdapter() : RecyclerView.Adapter<RegistrationViewHolder>() {

    private val daftarUlangList: ArrayList<DaftarUlang> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_registration, parent, false)
        return RegistrationViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return daftarUlangList.size
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
    }

}