package app.siakad.siakadtkadmin.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class UserDetailActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPasswd: TextView
    private lateinit var rvUserList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
    }

    private fun setupItemView() {
        tvName = findViewById(R.id.tv_user_detail_nama)
        tvEmail = findViewById(R.id.tv_user_detail_email)
        tvPasswd = findViewById(R.id.tv_user_detail_passwd)
        rvUserList = findViewById(R.id.rv_user_detail_daftar_data)
    }

    private fun setupView() {}
}