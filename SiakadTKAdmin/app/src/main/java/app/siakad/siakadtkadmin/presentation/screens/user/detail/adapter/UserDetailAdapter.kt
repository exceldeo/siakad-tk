package app.siakad.siakadtkadmin.presentation.screens.user.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class UserDetailAdapter() : RecyclerView.Adapter<UserDetailViewHolder>() {

    private val detailUserList: ArrayList<Unit> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_user_detail, parent, false)
        return UserDetailViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return detailUserList.size
    }

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) {
    }

}