package app.siakad.siakadtkadmin.presentation.screens.product.uniform.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.product.DetailSeragamModel
import app.siakad.siakadtkadmin.presentation.screens.product.uniform.helper.UniformAddHelper
import kotlinx.android.synthetic.main.item_uniform_size.view.*

class UniformSizeListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
  fun insertSize(item: DetailSeragamModel, pos: Int) {
    with(itemView) {
      tv_item_uniform_size_ukuran.text = "Ukuran: " + item.ukuran
      tv_item_uniform_size_jumlah.text = "Jumlah: " + item.jumlah.toString()
      tv_item_uniform_size_harga.text = "Harga: " + item.harga.toString()

      iv_item_uniform_size_hapus.setOnClickListener {
        (v.context as UniformAddHelper).deleteData(pos)
      }
    }
  }
}