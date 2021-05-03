package app.siakad.siakadtkadmin.presentation.screens.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan

class OrderListAdapter() : RecyclerView.Adapter<OrderListViewHolder>() {

    private val orderList: ArrayList<Pesanan> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.insertOrder(orderList[position])
    }

    fun changeDataList(data: ArrayList<Pesanan>) {
        if (orderList.size > 0)
            orderList.clear()

        orderList.addAll(data)
        this.notifyDataSetChanged()
    }
}