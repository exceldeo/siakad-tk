package app.siakad.siakadtk.presentation.nota

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.domain.models.PesananModel
import kotlinx.android.synthetic.main.item_grid_nota.view.*

class NotaViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertNota(item: PesananModel) {
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