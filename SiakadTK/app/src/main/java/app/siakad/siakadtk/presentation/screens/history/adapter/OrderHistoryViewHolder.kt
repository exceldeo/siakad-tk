package app.siakad.siakadtk.presentation.screens.history.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_order.view.*


class OrderHistoryViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
    @SuppressLint("SetTextI18n")
    fun insertNota(item: Pesanan, pos: Int) {
        with(itemView) {
            val imageArray = intArrayOf(
                R.drawable.ic_status_selesai
            )

            when (item.pesanan.statusPesan) {
                OrderStateModel.ORDER_DONE.str -> Picasso.with(v.context)
                    .load(imageArray[0]).placeholder(R.drawable.ic_status_selesai).into(
                    iv_item_order_status
                )
            }

            tv_item_order_title.text = "Nota " + item.pesanan.statusPesan + " " + pos.toString()

            var nameProduct = ""
            for (product in item.pesanan.detailPesanan!!.withIndex())
            {
                nameProduct += product.value.nama
                if(product.index != item.pesanan.detailPesanan!!.size - 1) nameProduct += ", "
            }

            tv_item_order_macam_produk.text = nameProduct
            tv_item_order_tgl_disetujui.text = item.pesanan.tanggalPesan
            var totalPayment = 0
            for (product in item.pesanan.detailPesanan!!.withIndex())
                totalPayment += product.value.harga * product.value.jumlah
            tv_item_order_total_harga.text = "Rp " + totalPayment.toString()
        }
    }
}