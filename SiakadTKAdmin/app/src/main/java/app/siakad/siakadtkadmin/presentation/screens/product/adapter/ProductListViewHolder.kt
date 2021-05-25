package app.siakad.siakadtkadmin.presentation.screens.product.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.presentation.screens.product.listener.ProductEditListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_list.view.*

class ProductListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
    fun insertUniform(item: SeragamModel) {
        with(itemView) {
            if (item.fotoProduk != "") {
                Picasso.with(v.context).load(item.fotoProduk).into(iv_item_product)
            }
            tv_item_product_nama.text = item.namaProduk
            tv_item_product_stok.text = "Stok: " + item.jumlah.toString()

            iv_item_product_edit.setOnClickListener {
                (v.context as ProductEditListener).navigateToUniformEdit(item)
            }
        }
    }

    fun insertBook(item: BukuModel) {
        with(itemView) {
            if (item.fotoProduk != "") {
                Picasso.with(v.context).load(item.fotoProduk).into(iv_item_product)
            }
            tv_item_product_nama.text = item.namaProduk
            tv_item_product_stok.text = "Stok: " + item.jumlah.toString()

            iv_item_product_edit.setOnClickListener {
                (v.context as ProductEditListener).navigateToBookEdit(item)
            }
        }
    }
}