package app.siakad.siakadtkadmin.presentation.screens.classroom.detail.siswa

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.presentation.screens.classroom.detail.ClassroomDetailActivity
import app.siakad.siakadtkadmin.presentation.screens.registration.RegistrationListFragment

class ClassroomSiswaDetailActivity: AppCompatActivity() {

  private val pageTitle = "Detail Siswa"

  private lateinit var tvName: TextView
  private lateinit var tvClass: TextView
  private lateinit var tvGender: TextView
  private lateinit var tvParent: TextView
  private lateinit var tvAddress: TextView
  private lateinit var tvHP: TextView
  private lateinit var tvPrice: TextView

  private var siswa: PenggunaModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_classroom_detail_detail)

    if (intent.getParcelableExtra<PenggunaModel>(ClassroomDetailActivity.DATA_SISWA) != null) {
      siswa = intent.getParcelableExtra(ClassroomDetailActivity.DATA_SISWA)
    }

    tvName = findViewById(R.id.tv_siswa_detail_nama)
    tvGender = findViewById(R.id.tv_siswa_detail_jenkel)
    tvParent = findViewById(R.id.tv_siswa_detail_wali)
    tvAddress = findViewById(R.id.tv_siswa_detail_alamat)
    tvHP = findViewById(R.id.tv_siswa_detail_nohp)

    if (siswa != null) {
      tvName.text = siswa?.nama
      tvGender.text = siswa?.detailPengguna?.jenisKelamin
      tvParent.text = siswa?.detailPengguna?.namaOrtu
      tvAddress.text = siswa?.alamat
      tvHP.text = siswa?.noHP
    }

    setupAppBar()
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

  private fun setupAppBar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
    setSupportActionBar(toolbar)
    supportActionBar?.title = pageTitle
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }


}