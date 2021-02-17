package app.siakad.siakadtkadmin.ui.order.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.data.model.Order
import app.siakad.siakadtkadmin.data.model.OrderDetail

class OrderDetailAdapter() : RecyclerView.Adapter<OrderDetailViewHolder>() {

    private val orderDetailList: ArrayList<OrderDetail> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderDetailViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return orderDetailList.size
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
    }

}