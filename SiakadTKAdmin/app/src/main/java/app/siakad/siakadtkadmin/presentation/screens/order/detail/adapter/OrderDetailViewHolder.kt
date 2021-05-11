package app.siakad.siakadtkadmin.presentation.screens.order.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.DetailKeranjangModel
import app.siakad.siakadtkadmin.infrastructure.data.DetailPesanan
import app.siakad.siakadtkadmin.presentation.screens.order.detail.helper.OrderDetailHelper
import com.google.android.material.checkbox.MaterialCheckBox
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_order_detail.view.*
import kotlinx.android.synthetic.main.item_product_list.view.*

class OrderDetailViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
  fun insertOrderDetail(item: DetailKeranjangModel, index: Int) {
    with(itemView) {
      Picasso.with(v.context).load(item.gambar).into(iv_item_order_detail)

      tv_item_order_detail_nama_produk.text = item.nama
      tv_item_order_detail_harga.text = "Rp. " + item.harga.toString()

      cb_item_order_detail_acc.setOnClickListener {
        if (!(cb_item_order_detail_acc as MaterialCheckBox).isChecked) {
          (v.context as OrderDetailHelper).uncheckTheItem(index)
        } else {
          (v.context as OrderDetailHelper).checkTheItem(index)
        }
      }
    }
  }
}