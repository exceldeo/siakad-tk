package app.siakad.siakadtkadmin.presentation.screens.order.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailKeranjangModel
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.infrastructure.data.DetailPesanan

class OrderDetailAdapter(private val type: String) : RecyclerView.Adapter<OrderDetailViewHolder>() {

    private val orderDetailList: ArrayList<DetailKeranjangModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return OrderDetailViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return orderDetailList.size
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.insertOrderDetail(orderDetailList[position], position, type)
    }

    fun changeDataList(data: ArrayList<DetailKeranjangModel>) {
        if (orderDetailList.size > 0)
            orderDetailList.clear()

        orderDetailList.addAll(data)
        this.notifyDataSetChanged()
    }
}