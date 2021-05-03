package app.siakad.siakadtkadmin.presentation.screens.registration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang

class RegistrationListAdapter() : RecyclerView.Adapter<RegistrationListViewHolder>() {

    private val daftarUlangList: ArrayList<DaftarUlang> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationListViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_registration, parent, false)
        return RegistrationListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return daftarUlangList.size
    }

    override fun onBindViewHolder(holder: RegistrationListViewHolder, position: Int) {
        holder.insertRegistration(daftarUlangList.get(position))
    }

    fun changeDataList(newRegistrationList: ArrayList<DaftarUlang>) {
        if (daftarUlangList.size > 0) {
            daftarUlangList.clear()
        }

        daftarUlangList.addAll(newRegistrationList)
        this.notifyDataSetChanged()
    }
}