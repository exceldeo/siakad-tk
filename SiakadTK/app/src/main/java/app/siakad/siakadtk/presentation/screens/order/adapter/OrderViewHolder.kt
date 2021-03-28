package app.siakad.siakadtk.presentation.screens.order.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.infrastructure.data.Pesanan
import kotlinx.android.synthetic.main.item_grid_order.view.*

class OrderViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertNota(item: Pesanan) {
        with(itemView) {
            tv_nota_title.text = item.pesananId
//            tv_nota_macam_produk.text = item.detailPesanan
//            tv_nota_tgl_disetujui.text = item.dateAccepted.toString()
//            tv_nota_total_harga.text = item.totalPrice.toString()
//            Glide.with(itemView.context)
//                .load(item.status)
//                .apply(RequestOptions())
//                .into(iv_nota_status)
        }
    }
}