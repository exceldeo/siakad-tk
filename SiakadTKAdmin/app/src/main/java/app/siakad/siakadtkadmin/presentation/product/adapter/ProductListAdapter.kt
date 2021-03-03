package app.siakad.siakadtkadmin.presentation.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.ProdukModel

class ProductListAdapter() : RecyclerView.Adapter<ProductListViewHolder>() {

    private val produk: ArrayList<ProdukModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return produk.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
    }

}