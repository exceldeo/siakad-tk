package app.siakad.siakadtkadmin.presentation.screens.user.detail.verified.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.user.adapter.UserListViewHolder

class UserDetailAdapter() : RecyclerView.Adapter<UserListViewHolder>() {

    private val detailUserList: ArrayList<Unit> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_user_detail, parent, false)
        return UserListViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return detailUserList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
    }

}