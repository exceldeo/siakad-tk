package app.siakad.siakadtkadmin.presentation.screens.order.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import app.siakad.siakadtkadmin.presentation.screens.order.helper.OrderClickHelper
import kotlinx.android.synthetic.main.item_order.view.*

class OrderListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
  fun insertOrder(item: Pesanan) {
    with(itemView) {
      tv_item_order_judul.text = item.pengguna.nama
      tv_item_order_kelas.text = item.pengguna.noHP
      tv_item_order_alamat.text = "Jumlah pesanan: " + item.pesanan.detailPesanan?.size.toString()
      tv_item_order_tanggal.text = item.pesanan.tanggalDipesan

      iv_item_order_detail.setOnClickListener {
        (v.context as OrderClickHelper).navigateToOrderDetail(item)
      }
    }
  }
}