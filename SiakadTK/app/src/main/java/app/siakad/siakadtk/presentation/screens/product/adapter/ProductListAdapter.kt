package app.siakad.siakadtk.presentation.screens.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.infrastructure.data.product.Seragam
import app.siakad.siakadtk.presentation.screens.main.product.adapter.ProductListViewHolder
import app.siakad.siakadtk.presentation.screens.product.utils.ProductType

class ProductListAdapter(private val type: ProductType) : RecyclerView.Adapter<ProductListViewHolder>() {

    private val seragam = arrayListOf<Seragam>()
    private val buku = arrayListOf<Buku>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_row_product_detail, parent, false)
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

    fun changeUniformList(data: ArrayList<Seragam>) {
        if (seragam.isNotEmpty()) {
            seragam.clear()
        }

        seragam.addAll(data)
        notifyDataSetChanged()
    }

    fun changeBookList(data: ArrayList<Buku>) {
        if (buku.isNotEmpty()) {
            buku.clear()
        }

        buku.addAll(data)
        notifyDataSetChanged()
    }
}