package app.siakad.siakadtkadmin.presentation.screens.registration.detail.unverified

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.registration.detail.RegistrationDetailUnverViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.registration.RegistrationListFragment
import app.siakad.siakadtkadmin.presentation.screens.user.detail.unverified.UserDetailUnverActivity
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener
import app.siakad.siakadtkadmin.presentation.views.preview.ImagePreviewActivity

class RegistrationDetailUnverActivity : AppCompatActivity(), AlertListener {

  private val pageTitle = "Detail Daftar Ulang"

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

  private lateinit var vmRegisDetail: RegistrationDetailUnverViewModel

  private var daful: DaftarUlang? = null

  companion object {
    const val TAG_CONFIRM = "Terima Daful"
    const val TAG_REJECT = "Tolak Daful"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_registration_detail_unver)

    if (intent.getParcelableExtra<DaftarUlang>(RegistrationListFragment.UNVERIFIED_REGISTRATION) != null) {
      daful = intent.getParcelableExtra(RegistrationListFragment.UNVERIFIED_REGISTRATION)
    }

    tvName = findViewById(R.id.tv_registration_detail_unver_nama)
    tvClass = findViewById(R.id.tv_registration_detail_unver_kelas)
    tvGender = findViewById(R.id.tv_registration_detail_unver_jenkel)
    tvParent = findViewById(R.id.tv_registration_detail_unver_wali)
    tvAddress = findViewById(R.id.tv_registration_detail_unver_alamat)
    tvHP = findViewById(R.id.tv_registration_detail_unver_nohp)
    tvPrice = findViewById(R.id.tv_registration_detail_unver_nobayar)

    if (daful != null) {
      tvName.text = daful?.pengguna?.nama
      tvClass.text = daful?.pengguna?.detailPengguna?.kelas
      tvGender.text = daful?.pengguna?.detailPengguna?.jenisKelamin
      tvParent.text = daful?.pengguna?.detailPengguna?.namaOrtu
      tvAddress.text = daful?.pengguna?.alamat
      tvHP.text = daful?.pengguna?.noHP
      tvPrice.text = ""
    }

    setupAppBar()
    setupViewModel()
    setupButtons()
  }

  private fun setupButtons() {
    btnProofPrice = findViewById(R.id.btn_registration_detail_unver_buktibayar)
    btnProofPrice.setOnClickListener {
      if (daful != null) {
        val intent = Intent(this@RegistrationDetailUnverActivity, ImagePreviewActivity::class.java)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE_TYPE, ImagePreviewActivity.IMG_URL)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE, daful?.daful?.fotoBayar!!)
        startActivity(intent)
      }
    }

    btnCancel = findViewById(R.id.btn_registration_detail_unver_batal)
    btnCancel.setOnClickListener {
      val alertDialog = AlertDialogFragment(
        "Tolak daftar ulang",
        "Apakah Anda yakin menolak daftar ulang ini?"
      )
      alertDialog.show(supportFragmentManager, TAG_CONFIRM)
    }

    btnSave = findViewById(R.id.btn_registration_detail_unver_simpan)
    btnSave.setOnClickListener {
      val alertDialog = AlertDialogFragment(
        "Konfirmasi daftar ulang",
        "Apakah Anda yakin melanjutkan konfirmasi?"
      )
      alertDialog.show(supportFragmentManager, TAG_CONFIRM)
    }
  }

  private fun setupAppBar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
    setSupportActionBar(toolbar)
    supportActionBar?.title = pageTitle
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun setupViewModel() {
    vmRegisDetail = ViewModelProvider(
      this,
      ViewModelFactory(this, this)
    ).get(RegistrationDetailUnverViewModel::class.java)
  }

  override fun alertAction(tag: String?) {
    if (tag == UserDetailUnverActivity.TAG_CONFIRM) {
      vmRegisDetail.updateDataToVerified(daful?.daful!!)
    } else if (tag == UserDetailUnverActivity.TAG_REJECT) {
      vmRegisDetail.removeData(daful?.daful!!)
      onBackPressed()
    }
  }
}