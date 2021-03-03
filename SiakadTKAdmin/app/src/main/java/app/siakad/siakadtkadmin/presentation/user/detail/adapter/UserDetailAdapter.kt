package app.siakad.siakadtkadmin.presentation.user.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailUser

class UserDetailAdapter() : RecyclerView.Adapter<UserDetailViewHolder>() {

    private val detailUserList: ArrayList<DetailUser> = arrayListOf()

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