package app.siakad.siakadtk.ui.nota

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.data.model.Nota
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NotaAdapter (val listNota: ArrayList<Nota>): RecyclerView.Adapter<NotaViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NotaViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_nota, viewGroup, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = listNota[position]
        holder.insertNota(nota)
    }

    override fun getItemCount(): Int {
        return listNota.size
    }
}