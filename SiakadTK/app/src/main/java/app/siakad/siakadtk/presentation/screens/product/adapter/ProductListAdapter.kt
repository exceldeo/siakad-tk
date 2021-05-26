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

    private lateinit var onItemClickCallbackBook: OnItemClickCallbackBook
    private lateinit var onItemClickCallbackUniform: OnItemClickCallbackUniform
    private val seragam = arrayListOf<Seragam>()
    private val buku = arrayListOf<Buku>()

    private var soldOutBook = 0
    private var soldOutUniform = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_row_product_detail, parent, false)
        return ProductListViewHolder(viewHolder)
    }

    fun setOnItemClickCallbackBook(onItemClickCallback: OnItemClickCallbackBook) {
        this.onItemClickCallbackBook = onItemClickCallback
    }

    fun setOnItemClickCallbackUniform(onItemClickCallback: OnItemClickCallbackUniform) {
        this.onItemClickCallbackUniform = onItemClickCallback
    }

    override fun getItemCount(): Int {
        return if (type == ProductType.SERAGAM) {
            seragam.size - soldOutUniform
        } else {
            buku.size - soldOutBook
        }
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        if (type == ProductType.SERAGAM) {
            if(seragam[position].jumlah >= 0) {
                holder.insertUniform(seragam[position])
                holder.itemView.setOnClickListener { onItemClickCallbackUniform.onItemClicked(seragam[holder.adapterPosition]) }
            } else {
                soldOutUniform++
            }
        } else {
            if(buku [position].jumlah >= 0) {
                holder.insertBook(buku[position])
                holder.itemView.setOnClickListener { onItemClickCallbackBook.onItemClicked(buku[holder.adapterPosition]) }
            } else {
                soldOutBook++
            }
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

    interface OnItemClickCallbackUniform {
        fun onItemClicked(data: Seragam)
    }

    interface OnItemClickCallbackBook {
        fun onItemClicked(data: Buku)
    }
}