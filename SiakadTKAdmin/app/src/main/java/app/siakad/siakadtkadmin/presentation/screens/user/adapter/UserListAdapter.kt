package app.siakad.siakadtkadmin.presentation.screens.user.detail.verified.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.presentation.screens.user.adapter.UserListViewHolder

class UserListAdapter() : RecyclerView.Adapter<UserListViewHolder>() {

    private val userList: ArrayList<PenggunaModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
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