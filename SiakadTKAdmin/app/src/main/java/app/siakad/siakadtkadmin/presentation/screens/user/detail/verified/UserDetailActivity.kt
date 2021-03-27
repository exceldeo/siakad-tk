package app.siakad.siakadtkadmin.presentation.screens.user.detail.verified

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailPenggunaModel
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.detail.UserDetailViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.user.UserListFragment

class UserDetailActivity : AppCompatActivity() {

    private val pageTitle = "Detail Pengguna"

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPasswd: TextView

    private lateinit var rvUserList: RecyclerView
    private lateinit var vmUserDetail: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        setupAppBar()

        setupView()

        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@UserDetailActivity,
                        UserDetailActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        val siswa = intent.getParcelableExtra<Siswa>(UserListFragment.VERIFIED_USER)

        tvName = findViewById(R.id.tv_user_detail_nama)
        tvName.text = siswa.nama

        tvEmail = findViewById(R.id.tv_user_detail_email)
        tvEmail.text = siswa.email

        tvPasswd = findViewById(R.id.tv_user_detail_passwd)
        tvPasswd.transformationMethod = PasswordTransformationMethod()
        tvPasswd.text = siswa.passwd

        rvUserList = findViewById(R.id.rv_user_detail_daftar_data)
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        val siswa = intent.getParcelableExtra<Siswa>(UserListFragment.UNVERIFIED_USER)

        vmUserDetail = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(UserDetailViewModel::class.java)
        vmUserDetail.setUserId(siswa.userId)

        val obsUserDetail = Observer<DetailPenggunaModel> { detail ->
        }
        vmUserDetail.getUserDetail().observe(this, obsUserDetail)
    }
}