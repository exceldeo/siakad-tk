package app.siakad.siakadtkadmin.ui.order.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.data.model.Order
import app.siakad.siakadtkadmin.data.model.OrderDetail
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.item_order_detail.view.*

class OrderDetailViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun itemOrder(item: OrderDetail) {
        with(itemView) {
            iv_item_order_detail
            tv_item_order_detail_nama_produk.text = item.namaProduk
            tv_item_order_detail_harga.text = item.harga
            item.status

            iv_item_order_detail_acc.setOnClickListener {  }
            iv_item_order_detail_reject.setOnClickListener {  }
        }
    }
}