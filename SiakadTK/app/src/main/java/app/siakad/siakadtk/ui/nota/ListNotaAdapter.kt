package app.siakad.siakadtk.ui.nota

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.ui.profile.ListUserActivitiesAdapter
import app.siakad.siakadtk.ui.profile.UserActivities
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListNotaAdapter (val listNota: ArrayList<Nota>): RecyclerView.Adapter<ListNotaAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_nota, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val nota = listNota[position]

        Glide.with(holder.itemView.context)
            .load(nota.status)
            .apply(RequestOptions())
            .into(holder.ivStatus)

        holder.tvTitle.text = nota.title
        holder.tvProducts.text = nota.product
        holder.tvDateAccepted.text = nota.dateAccepted
        holder.tvTotalPrice.text = nota.totalPrice.toString()
    }

    override fun getItemCount(): Int {
        return listNota.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_nota_title)
        var tvProducts: TextView = itemView.findViewById(R.id.tv_nota_macam_produk)
        var tvDateAccepted: TextView = itemView.findViewById(R.id.tv_nota_tgl_disetujui)
        var tvTotalPrice: TextView = itemView.findViewById(R.id.tv_nota_total_harga)
        var ivStatus: ImageView = itemView.findViewById(R.id.iv_nota_status)
    }
}