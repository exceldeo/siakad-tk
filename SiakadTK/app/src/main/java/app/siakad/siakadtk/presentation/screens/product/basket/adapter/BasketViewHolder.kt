package app.siakad.siakadtk.presentation.screens.product.basket.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.infrastructure.data.DetailKeranjang
import app.siakad.siakadtk.infrastructure.data.Keranjang
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_basket.view.*

class BasketViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertKeranjang(item: DetailKeranjang) {
        with(itemView) {
            Picasso.get().load(item.gambar).into(riv_item_basket_product_img)
            tv_item_basket_product_name.text = item.nama
            tv_item_basket_product_price.text = "Total : Rp " + item.harga.toString()
            tv_item_basket_product_jumlah.text = item.jumlah.toString() + " buah"
            if (item.ukuran != "") tv_item_basket_product_ukuran.text = item.ukuran
            else tv_item_basket_product_ukuran.visibility = View.INVISIBLE        }
    }
}