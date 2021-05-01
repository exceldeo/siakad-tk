package app.siakad.siakadtkadmin.presentation.screens.classroom.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.presentation.screens.classroom.listener.ClassroomClickListener
import kotlinx.android.synthetic.main.item_classroom.view.*

class ClassroomListViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
    fun insertClassroom(item: KelasModel) {
        with(itemView) {
            tv_item_classroom_judul.text = item.namaKelas
            tv_item_classroom_kelas.text = item.tahunMulai.toString() + " - " + item.tahunSelesai.toString()

            iv_item_classroom_detail.setOnClickListener{
                (v.context as ClassroomClickListener).navigateToClassroomDetail(item)
            }
        }
    }
}