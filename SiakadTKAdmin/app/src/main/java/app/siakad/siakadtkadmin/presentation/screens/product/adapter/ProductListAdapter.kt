package app.siakad.siakadtkadmin.presentation.screens.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.infrastructure.data.product.Buku
import app.siakad.siakadtkadmin.infrastructure.data.product.Seragam
import app.siakad.siakadtkadmin.presentation.screens.product.utils.ProductType

class ProductListAdapter(private val type: ProductType) : RecyclerView.Adapter<ProductListViewHolder>() {

    private val seragam = arrayListOf<SeragamModel>()
    private val buku = arrayListOf<BukuModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return if (type == ProductType.SERAGAM) {
            seragam.size
        } else {
            buku.size
        }
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        if (type == ProductType.SERAGAM) {
            holder.insertUniform(seragam[position])
        } else {
            holder.insertBook(buku[position])
        }
    }

    fun changeUniformList(data: ArrayList<SeragamModel>) {
        if (seragam.isNotEmpty()) {
            seragam.clear()
        }

        seragam.addAll(data)
        notifyDataSetChanged()
    }

    fun changeBookList(data: ArrayList<BukuModel>) {
        if (buku.isNotEmpty()) {
            buku.clear()
        }

        buku.addAll(data)
        notifyDataSetChanged()
    }
}