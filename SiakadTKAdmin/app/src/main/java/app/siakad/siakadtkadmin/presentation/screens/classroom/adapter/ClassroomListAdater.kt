package app.siakad.siakadtkadmin.presentation.screens.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Kelas

class ClassroomListAdater : RecyclerView.Adapter<ClassroomListViewHolder>() {

    private val classroomList: ArrayList<Kelas> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassroomListViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_classroom, parent, false)
        return ClassroomListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return classroomList.size
    }

    override fun onBindViewHolder(holder: ClassroomListViewHolder, position: Int) {
        holder.insertAnnouncement(classroomList[position])
    }

    fun changeDataList(data: ArrayList<Kelas>) {
        if (classroomList.size > 0)
            classroomList.clear()

        classroomList.addAll(data)
        this.notifyDataSetChanged()
    }
}