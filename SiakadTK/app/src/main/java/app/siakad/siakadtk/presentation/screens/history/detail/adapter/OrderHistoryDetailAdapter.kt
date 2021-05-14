package app.siakad.siakadtk.presentation.screens.history.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel

class OrderHistoryDetailAdapter(private  val orderList: ArrayList<DetailKeranjangModel>): RecyclerView.Adapter<OrderHistoryDetailViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderHistoryDetailViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_orderdetail_product, viewGroup, false)
        return OrderHistoryDetailViewHolder(view)
    }

    override fun onBindViewHolder(holderDetail: OrderHistoryDetailViewHolder, position: Int) {
        holderDetail.insertOrderDetail(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun changeDataList(data: ArrayList<DetailKeranjangModel>) {
        if (orderList.size > 0)
            orderList.clear()

        orderList.addAll(data)
        this.notifyDataSetChanged()
    }
}