package app.siakad.siakadtkadmin.ui.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.announcement.AnnouncementAddActivity
import app.siakad.siakadtkadmin.ui.notification.NotificationActivity

class RegistrationDetailActivity : AppCompatActivity() {

    private val pageTitle = "Detail Daftar Ulang"

    private lateinit var toolbar: Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvClass: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvParent: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvHP: TextView
    private lateinit var tvPrice: TextView
    private lateinit var btnProofPrice: CardView
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_detail)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvName = findViewById(R.id.tv_registration_detail_nama)
        tvClass = findViewById(R.id.tv_registration_detail_kelas)
        tvGender = findViewById(R.id.tv_registration_detail_jenkel)
        tvParent = findViewById(R.id.tv_registration_detail_wali)
        tvAddress = findViewById(R.id.tv_registration_detail_alamat)
        tvHP = findViewById(R.id.tv_registration_detail_nohp)
        tvPrice = findViewById(R.id.tv_registration_detail_nobayar)
        btnProofPrice = findViewById(R.id.btn_registration_detail_buktibayar)
        btnCancel = findViewById(R.id.btn_registration_detail_batal)
        btnSave = findViewById(R.id.btn_registration_detail_simpan)
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