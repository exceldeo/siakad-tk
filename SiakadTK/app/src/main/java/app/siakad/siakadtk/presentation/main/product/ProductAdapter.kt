package app.siakad.siakadtk.presentation.main.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.ProdukModel

class ProductAdapter (val productList: ArrayList<ProdukModel>): RecyclerView.Adapter<ProductViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_product, viewGroup, false)
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