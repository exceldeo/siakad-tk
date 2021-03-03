package app.siakad.siakadtkadmin.presentation.product.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.data.model.Order
import app.siakad.siakadtkadmin.data.model.ProductList
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.item_product_list.view.*

class ProductListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun itemOrder(item: ProductList) {
        with(itemView) {
            iv_item_product
            tv_item_product_nama.text = item.namaProduk
            tv_item_product_stok.text = item.jumlahStok.toString()
            item.foto

            iv_item_product_edit.setOnClickListener {  }
        }
    }
}