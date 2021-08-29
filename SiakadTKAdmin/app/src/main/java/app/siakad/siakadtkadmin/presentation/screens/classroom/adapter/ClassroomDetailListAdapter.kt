package app.siakad.siakadtkadmin.presentation.screens.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.presentation.screens.classroom.detail.ClassroomDetailViewHolder
import app.siakad.siakadtkadmin.presentation.screens.user.adapter.UserListViewHolder

class ClassroomDetailListAdapter() : RecyclerView.Adapter<ClassroomDetailViewHolder>() {

  private val userList: ArrayList<PenggunaModel> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassroomDetailViewHolder {
    val viewHolder =
      LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
    return ClassroomDetailViewHolder(viewHolder)
  }

  override fun getItemCount(): Int {
    return userList.size
  }

  override fun onBindViewHolder(holder: ClassroomDetailViewHolder, position: Int) {
    holder.insertUser(userList.get(position))
  }

  fun changeDataList(newUserList: ArrayList<PenggunaModel>) {
    if (userList.size > 0) {
      userList.clear()
    }

    userList.addAll(newUserList)
    this.notifyDataSetChanged()
  }
}