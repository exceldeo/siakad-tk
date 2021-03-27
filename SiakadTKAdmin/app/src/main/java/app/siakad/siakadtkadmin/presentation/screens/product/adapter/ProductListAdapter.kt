package app.siakad.siakadtkadmin.presentation.screens.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.product.utils.ProductType

class ProductListAdapter(private val type: ProductType) : RecyclerView.Adapter<ProductListViewHolder>() {

    private val produk: ArrayList<Any> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return produk.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
    }

    fun changeDataList(data: ArrayList<Any>) {

    }
}