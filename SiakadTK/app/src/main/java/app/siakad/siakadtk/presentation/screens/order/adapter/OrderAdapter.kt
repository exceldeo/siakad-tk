package app.siakad.siakadtk.presentation.screens.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pesanan

class OrderAdapter(): RecyclerView.Adapter<OrderViewHolder>() {
    private  val orderList: ArrayList<Pesanan> = arrayListOf()
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_order, viewGroup, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.insertNota(orderList[position], position)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun changeDataList(data: ArrayList<Pesanan>) {
        if (orderList.size > 0)
            orderList.clear()

        orderList.addAll(data)
        this.notifyDataSetChanged()
    }
}