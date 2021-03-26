package app.siakad.siakadtk.presentation.main.product.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.infrastructure.data.Produk
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_row_product_detail.view.*

class ProductListViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertProduct(item: Produk) {
        with(itemView) {
            tv_item_product_title.text = item.namaProduk
            tv_item_product_jumlah.text = item.jumlah.toString()
            Glide.with(itemView.context)
                .load(item.fotoProduk)
                .apply(RequestOptions())
                .into(riv_item_product_img)
        }
    }
}