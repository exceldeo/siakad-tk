package app.siakad.siakadtkadmin.presentation.screens.registration.detail.verified

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.presentation.screens.registration.RegistrationListFragment
import app.siakad.siakadtkadmin.presentation.views.preview.ImagePreviewActivity

class RegistrationDetailActivity : AppCompatActivity() {

  private val pageTitle = "Detail Daftar Ulang"

  private lateinit var tvName: TextView
  private lateinit var tvClass: TextView
  private lateinit var tvGender: TextView
  private lateinit var tvParent: TextView
  private lateinit var tvAddress: TextView
  private lateinit var tvHP: TextView
  private lateinit var tvPrice: TextView
  private lateinit var btnProofPrice: CardView

  private var daful: DaftarUlang? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_registration_detail)

    if (intent.getParcelableExtra<DaftarUlang>(RegistrationListFragment.VERIFIED_REGISTRATION) != null) {
      daful = intent.getParcelableExtra(RegistrationListFragment.VERIFIED_REGISTRATION)
    }

    tvName = findViewById(R.id.tv_registration_detail_nama)
    tvClass = findViewById(R.id.tv_registration_detail_kelas)
    tvGender = findViewById(R.id.tv_registration_detail_jenkel)
    tvParent = findViewById(R.id.tv_registration_detail_wali)
    tvAddress = findViewById(R.id.tv_registration_detail_alamat)
    tvHP = findViewById(R.id.tv_registration_detail_nohp)
    tvPrice = findViewById(R.id.tv_registration_detail_nobayar)

    if (daful != null) {
      tvName.text = daful?.pengguna?.pengguna?.nama
      tvClass.text = daful?.pengguna?.kelas?.namaKelas
      tvGender.text = daful?.pengguna?.pengguna?.detailPengguna?.jenisKelamin
      tvParent.text = daful?.pengguna?.pengguna?.detailPengguna?.namaOrtu
      tvAddress.text = daful?.pengguna?.pengguna?.alamat
      tvHP.text = daful?.pengguna?.pengguna?.noHP
      tvPrice.text = ""
    }

    setupAppBar()
    setupItemView()
  }

  private fun setupItemView() {
    btnProofPrice = findViewById(R.id.btn_registration_detail_buktibayar)
    btnProofPrice.setOnClickListener {
      if (daful != null) {
        val intent = Intent(this@RegistrationDetailActivity, ImagePreviewActivity::class.java)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE_TYPE, ImagePreviewActivity.IMG_URL)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE, daful?.daful?.fotoBayar!!)
        startActivity(intent)
      }
    }
  }

  private fun setupAppBar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
    setSupportActionBar(toolbar)
    supportActionBar?.title = pageTitle
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }
}