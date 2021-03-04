package app.siakad.siakadtk.presentation.main.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.ProdukModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_grid_product.view.*

class ProductViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertProduct(item: ProdukModel) {
        with(itemView) {
            tv_item_product_title.text = item.namaProduk
            tv_item_product_deadline.text = item.tanggal.toString()
            tv_item_product_status.text = item.status
            Glide.with(itemView.context)
                .load(item.fotoProduk)
                .apply(RequestOptions())
                .into(riv_item_product_img)
        }
    }
}