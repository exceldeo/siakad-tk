package app.siakad.siakadtk.ui.nota

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.data.model.Nota
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_grid_nota.view.*

class NotaViewHolder(v: View): RecyclerView.ViewHolder(v) {
    fun insertNota(item: Nota) {
        with(itemView) {
            tv_nota_title.text = item.title
            tv_nota_macam_produk.text = item.product
            tv_nota_tgl_disetujui.text = item.dateAccepted.toString()
            tv_nota_total_harga.text = item.totalPrice.toString()
            Glide.with(itemView.context)
                .load(item.status)
                .apply(RequestOptions())
                .into(iv_nota_status)
        }
    }
}