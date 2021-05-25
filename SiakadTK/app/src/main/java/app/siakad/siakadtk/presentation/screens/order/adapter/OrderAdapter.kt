package app.siakad.siakadtk.presentation.screens.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.presentation.screens.product.adapter.ProductListAdapter

class OrderAdapter(): RecyclerView.Adapter<OrderViewHolder>() {
    private  val orderList: ArrayList<Pesanan> = arrayListOf()
    private lateinit var onItemClickCallback: OnItemClickCallback
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_order, viewGroup, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        if(orderList[position].pesanan.statusPesan != OrderStateModel.ORDER_DONE.str)
            holder.insertNota(orderList[position], position)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(orderList[holder.adapterPosition]) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OrderAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
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

    interface OnItemClickCallback {
        fun onItemClicked(data: Pesanan)
    }
}