package app.siakad.siakadtkadmin.ui.user.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.data.model.User
import app.siakad.siakadtkadmin.data.model.UserDetail

class UserDetailAdapter() : RecyclerView.Adapter<UserDetailViewHolder>() {

    private val userDetailList: ArrayList<UserDetail> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_user_detail, parent, false)
        return UserDetailViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return userDetailList.size
    }

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) {
    }

}