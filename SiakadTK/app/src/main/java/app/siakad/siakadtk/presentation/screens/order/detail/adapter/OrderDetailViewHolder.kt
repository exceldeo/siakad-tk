package app.siakad.siakadtk.presentation.screens.order.detail.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_order.view.*
import kotlinx.android.synthetic.main.item_row_orderdetail_product.view.*


class OrderDetailViewHolder(v: View): RecyclerView.ViewHolder(v) {
    @SuppressLint("SetTextI18n")
    fun insertOrderDetail(item: DetailKeranjangModel) {
        with(itemView) {
            tv_item_orderdetail_title.text = item.nama
            tv_item_orderdetail_price.text = item.harga.toString()

            if(item.ukuran != "") tv_item_orderdetail_jumlah_ukuran.text = item.jumlah.toString() + " [" + item.ukuran + "]"
            else tv_item_orderdetail_jumlah_ukuran.text = item.jumlah.toString()
            tv_item_orderdetail_total_payment.text = "Rp " + (item.harga * item.jumlah).toString()
        }
    }
}