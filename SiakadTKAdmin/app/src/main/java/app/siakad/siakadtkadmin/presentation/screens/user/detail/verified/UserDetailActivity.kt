package app.siakad.siakadtkadmin.presentation.screens.user.detail.verified

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
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

    private var siswa: PenggunaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        if (intent.getParcelableExtra<PenggunaModel>(UserListFragment.UNVERIFIED_USER) != null) {
            siswa = intent.getParcelableExtra(UserListFragment.UNVERIFIED_USER)
        }

        tvName = findViewById(R.id.tv_user_detail_nama)
        tvEmail = findViewById(R.id.tv_user_detail_email)
        tvPasswd = findViewById(R.id.tv_user_detail_passwd)

        if (siswa != null) {
            tvName.text = siswa?.nama
            tvEmail.text = siswa?.email
            tvPasswd.transformationMethod = PasswordTransformationMethod()
            tvPasswd.text = siswa?.passwd
        }

        setupAppBar()
        setupItemList()
        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemList() {
        rvUserList = findViewById(R.id.rv_user_detail_daftar_data)
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        vmUserDetail = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(UserDetailViewModel::class.java)
    }
}