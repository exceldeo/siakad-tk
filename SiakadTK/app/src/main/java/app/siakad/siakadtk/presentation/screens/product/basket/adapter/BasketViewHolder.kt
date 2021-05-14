package app.siakad.siakadtk.presentation.screens.product.basket.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.utils.listeners.basket.BasketAddListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_basket.view.*
import kotlinx.android.synthetic.main.item_row_basket.view.*


class BasketViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
    fun insertKeranjang(item: DetailKeranjangModel, pos: Int) {
        with(itemView) {
            Picasso.with(v.context).load(item.gambar).into(riv_item_basket_product_img)
            tv_item_basket_product_name.text = item.nama
            tv_item_basket_product_price.text = "Total : Rp " + item.harga.toString()
            tv_item_basket_product_jumlah.text = item.jumlah.toString() + " buah"
            if (item.ukuran != "") tv_item_basket_product_ukuran.text = item.ukuran
            else tv_item_basket_product_ukuran.visibility = View.INVISIBLE

            cb_item_basket.isChecked = (v.context as BasketAddListener).getStatusAllChecked()

            cb_item_basket.setOnCheckedChangeListener { compoundButton, b ->
                if(compoundButton.isChecked) {
                    (v.context as BasketAddListener).addCheckedItem(pos)
                } else {
                    (v.context as BasketAddListener).addUncheckedItem(pos)
                }
            }
        }
    }
}