package app.siakad.siakadtkadmin.presentation.registration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.data.model.Order
import app.siakad.siakadtkadmin.data.model.Registration

class RegistrationAdapter() : RecyclerView.Adapter<RegistrationViewHolder>() {

    private val registrationList: ArrayList<Registration> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_registration, parent, false)
        return RegistrationViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return registrationList.size
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
    }

}