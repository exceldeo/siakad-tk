package app.siakad.siakadtk.presentation.nota

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.data.db.childs.Pesanan
import app.siakad.siakadtk.domain.models.PesananModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_grid_nota.view.*

class NotaViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertNota(item: PesananModel) {
        with(itemView) {
            tv_nota_title.text = "Nota Pemesanan" + item.pesananId
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