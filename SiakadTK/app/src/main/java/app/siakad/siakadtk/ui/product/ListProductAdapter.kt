package app.siakad.siakadtk.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListProductAdapter (val listProduct: ArrayList<Product>): RecyclerView.Adapter<ListProductAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_product, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val product = listProduct[position]

        holder.tvTitle.text = product.title
        holder.tvStatus.text = product.status
        holder.tvDateDeadline.text = product.dateDeadline
        Glide.with(holder.itemView.context)
            .load(product.img)
            .apply(RequestOptions())
            .into(holder.ivImage)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_item_product_title)
        var tvStatus: TextView = itemView.findViewById(R.id.tv_item_product_status)
        var tvDateDeadline: TextView = itemView.findViewById(R.id.tv_item_product_deadline)
        var ivImage: ImageView = itemView.findViewById(R.id.riv_item_product_img)
    }
}