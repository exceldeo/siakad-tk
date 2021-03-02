package app.siakad.siakadtkadmin.ui.user.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
<<<<<<< HEAD:SiakadTKAdmin/app/src/main/java/app/siakad/siakadtkadmin/ui/user/detail/UserDetailActivity.kt
<<<<<<< HEAD:SiakadTKAdmin/app/src/main/java/app/siakad/siakadtkadmin/ui/user/detail/UserDetailActivity.kt
import app.siakad.siakadtkadmin.ui.user.UserActivity
=======
>>>>>>> parent of 94db125... back press handle:SiakadTKAdmin/app/src/main/java/app/siakad/siakadtkadmin/ui/user/UserDetailActivity.kt
=======
>>>>>>> parent of 94db125... back press handle:SiakadTKAdmin/app/src/main/java/app/siakad/siakadtkadmin/ui/user/UserDetailActivity.kt

class UserDetailActivity : AppCompatActivity() {

    private val pageTitle = "Detail Pengguna"

    private lateinit var toolbar: Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPasswd: TextView
    private lateinit var rvUserList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvName = findViewById(R.id.tv_user_detail_nama)
        tvEmail = findViewById(R.id.tv_user_detail_email)
        tvPasswd = findViewById(R.id.tv_user_detail_passwd)
        rvUserList = findViewById(R.id.rv_user_detail_daftar_data)
    }

    private fun setupView() {
        setupAppBar()
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}