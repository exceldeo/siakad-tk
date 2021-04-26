package app.siakad.siakadtkadmin.presentation.screens.product.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.infrastructure.data.product.Buku
import app.siakad.siakadtkadmin.infrastructure.data.product.Produk
import app.siakad.siakadtkadmin.infrastructure.data.product.Seragam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_list.view.*

class ProductListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun insertUniform(item: SeragamModel) {
        with(itemView) {
            Picasso.get().load(item.fotoProduk).into(iv_item_product);
            tv_item_product_nama.text = item.namaProduk
            tv_item_product_stok.text = "Stok: " + item.jumlah.toString()

            iv_item_product_edit.setOnClickListener {  }
        }
    }

    fun insertBook(item: BukuModel) {
        with(itemView) {
            Picasso.get().load(item.fotoProduk).into(iv_item_product);
            tv_item_product_nama.text = item.namaProduk
            tv_item_product_stok.text = "Stok: " + item.jumlah.toString()

            iv_item_product_edit.setOnClickListener {  }
        }
    }
}