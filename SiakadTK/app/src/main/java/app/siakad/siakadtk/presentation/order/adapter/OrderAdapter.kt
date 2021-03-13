package app.siakad.siakadtk.presentation.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pesanan

class OrderAdapter (val listOrder: ArrayList<Pesanan>): RecyclerView.Adapter<OrderViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_order, viewGroup, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val nota = listOrder[position]
        holder.insertNota(nota)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }
}