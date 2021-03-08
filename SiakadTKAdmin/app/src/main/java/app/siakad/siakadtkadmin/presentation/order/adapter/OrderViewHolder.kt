package app.siakad.siakadtkadmin.presentation.order.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import kotlinx.android.synthetic.main.item_order.view.*

class OrderViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun insertOrder(item: Pesanan) {
        with(itemView) {
            iv_item_order
//            tv_item_order_judul.text = item.namaSiswa
//            tv_item_order_kelas.text = item.kelas
//            tv_item_order_alamat.text = item.jumlahPasanan
//            tv_item_order_tanggal.text = item.tanggalPesan
        }
    }
}