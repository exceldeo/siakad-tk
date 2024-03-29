package app.siakad.siakadtk.presentation.screens.main.product.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.product.BukuModel
import app.siakad.siakadtk.domain.models.product.SeragamModel
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.infrastructure.data.product.Produk
import app.siakad.siakadtk.infrastructure.data.product.Seragam
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_basket.view.*
import kotlinx.android.synthetic.main.item_row_product_detail.view.*

class ProductListViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
    fun insertUniform(item: SeragamModel) {
        with(itemView) {
            if(item.fotoProduk != "") Picasso.with(v.context).load(item.fotoProduk).into(riv_item_product_img)
            tv_item_product_title.text = item.namaProduk
            tv_item_product_jumlah.text = "Stok: " + item.jumlah.toString()
        }
    }

    fun insertBook(item: BukuModel) {
        with(itemView) {
            if(item.fotoProduk != "") Picasso.with(v.context).load(item.fotoProduk).into(riv_item_product_img)
            tv_item_product_title.text = item.namaProduk
            tv_item_product_jumlah.text = "Stok: " + item.jumlah.toString()
        }
    }
}