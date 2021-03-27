package app.siakad.siakadtkadmin.presentation.screens.user.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R

class UserDetailUnverActivity : AppCompatActivity() {

    private val pageTitle = "Detail Pengguna"

    private lateinit var toolbar: Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPasswd: TextView
    private lateinit var btnProofPay: CardView
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_unver)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvName = findViewById(R.id.tv_user_detail_unver_nama)
        tvEmail = findViewById(R.id.tv_user_detail_unver_email)
        tvPasswd = findViewById(R.id.tv_user_detail_unver_passwd)
        btnProofPay = findViewById(R.id.btn_user_detail_unver_buktibayar)
        btnCancel = findViewById(R.id.btn_user_detail_unver_batal)
        btnSave = findViewById(R.id.btn_user_detail_unver_simpan)
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