package app.siakad.siakadtk.presentation.screens.order.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_order.view.*


class OrderViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
    @SuppressLint("SetTextI18n")
    fun insertNota(item: Pesanan, pos: Int) {
        with(itemView) {
            val imageArray = intArrayOf(
                R.drawable.ic_status_menunggu_pembayaran,
                R.drawable.ic_status_menunggu_diproses,
                R.drawable.ic_status_proses,
                R.drawable.ic_status_revisi,
                R.drawable.ic_status_selesai
            )

            var statusPesan = item.pesanan.statusPesan
            when (statusPesan) {
                OrderStateModel.ORDER_PENDING.str -> {
                    tv_item_order_tgl_disetujui.text = "Tanggal Dipesan : " + item.pesanan.tanggalDipesan
                    Picasso.with(v.context)
                        .load(imageArray[0]).placeholder(R.drawable.ic_status_menunggu_pembayaran).into(
                            iv_item_order_status)
                }
                OrderStateModel.ORDER_PROCESS.str -> {
                    tv_item_order_tgl_disetujui.text = "Tanggal Diproses : " + item.pesanan.tanggalDiproses
                    Picasso.with(v.context)
                        .load(imageArray[2]).placeholder(R.drawable.ic_status_proses).into(
                            iv_item_order_status)
                }
                OrderStateModel.ORDER_REVISION.str -> {
                    tv_item_order_tgl_disetujui.text = "Tanggal Dipesan : " + item.pesanan.tanggalDipesan
                    Picasso.with(v.context)
                        .load(imageArray[3]).placeholder(R.drawable.ic_status_revisi).into(
                            iv_item_order_status)
                }
                OrderStateModel.ORDER_PROCESS.str -> {
                    tv_item_order_tgl_disetujui.text = "Tanggal Selesai : " + item.pesanan.tanggalSelesai
                    Picasso.with(v.context)
                        .load(imageArray[4]).placeholder(R.drawable.ic_status_selesai).into(
                            iv_item_order_status)
                }
            }

            if(item.pesanan.fotoBayar != "" && item.pesanan.statusPesan == OrderStateModel.ORDER_PENDING.str) {
                Picasso.with(v.context)
                    .load(imageArray[1]).placeholder(R.drawable.ic_status_menunggu_diproses).into(
                        iv_item_order_status)
            }

            tv_item_order_title.text = "Nota " + pos.toString() + " : " + item.pesanan.statusPesan
            var nameProduct = ""
            for (product in item.pesanan.detailPesanan!!.withIndex())
            {
                nameProduct += product.value.nama
                if(product.index != item.pesanan.detailPesanan!!.size - 1) nameProduct += ", "
            }

            tv_item_order_macam_produk.text = nameProduct
            var totalPayment = 0
            for (product in item.pesanan.detailPesanan!!.withIndex())
                totalPayment += product.value.harga * product.value.jumlah
            tv_item_order_total_harga.text = "Rp " + totalPayment.toString()
        }
    }
}