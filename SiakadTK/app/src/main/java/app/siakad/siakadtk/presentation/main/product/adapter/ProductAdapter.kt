package app.siakad.siakadtk.presentation.main.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Produk
import app.siakad.siakadtk.presentation.main.product.adapter.ProductViewHolder

class ProductAdapter (val productList: ArrayList<Produk>): RecyclerView.Adapter<ProductViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_product_type, viewGroup, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.insertProduct(product)

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}