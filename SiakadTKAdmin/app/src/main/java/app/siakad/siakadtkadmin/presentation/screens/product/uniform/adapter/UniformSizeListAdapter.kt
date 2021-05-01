package app.siakad.siakadtkadmin.presentation.screens.product.uniform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.product.DetailSeragamModel
import app.siakad.siakadtkadmin.presentation.screens.product.utils.ProductType

class UniformSizeListAdapter() : RecyclerView.Adapter<UniformSizeListViewHolder>() {

    private val sizeList: ArrayList<DetailSeragamModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniformSizeListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_uniform_size, parent, false)
        return UniformSizeListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return sizeList.size
    }

    override fun onBindViewHolder(holder: UniformSizeListViewHolder, position: Int) {
        holder.insertSize(sizeList[position])
    }

    fun addData(data: DetailSeragamModel) {
        sizeList.add(data)
        notifyDataSetChanged()
    }

    fun addAllData(data: ArrayList<DetailSeragamModel>) {
        sizeList.addAll(data)
        notifyDataSetChanged()
    }
}