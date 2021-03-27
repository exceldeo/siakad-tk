package app.siakad.siakadtkadmin.presentation.screens.product.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.product.Produk
import kotlinx.android.synthetic.main.item_product_list.view.*

class ProductListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun itemOrder(item: Produk) {
        with(itemView) {
            iv_item_product
            tv_item_product_nama.text = item.namaProduk
            tv_item_product_stok.text = item.jumlah.toString()
            item.fotoProduk

            iv_item_product_edit.setOnClickListener {  }
        }
    }
}