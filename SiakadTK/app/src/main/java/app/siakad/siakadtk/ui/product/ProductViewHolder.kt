package app.siakad.siakadtk.ui.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.data.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_grid_product.view.*

class ProductViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertProduct(item: Product) {
        with(itemView) {
            tv_item_product_title.text = item.title
            tv_item_product_deadline.text = item.dateDeadline.toString()
            tv_item_product_status.text = item.status
            Glide.with(itemView.context)
                .load(item.img)
                .apply(RequestOptions())
                .into(riv_item_product_img)
        }
    }
}