package app.siakad.siakadtkadmin.presentation.screens.user.detail.unverified

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailPenggunaModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.detail.UserDetailViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.user.UserListFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener
import app.siakad.siakadtkadmin.presentation.views.preview.ImagePreviewActivity
import com.google.android.material.button.MaterialButton

class UserDetailUnverActivity : AppCompatActivity(), AlertListener {

  private val pageTitle = "Detail Pengguna"

  private lateinit var tvName: TextView
  private lateinit var tvEmail: TextView
  private lateinit var tvPasswd: TextView
  private lateinit var btnProofPay: MaterialButton
  private lateinit var btnCancel: MaterialButton
  private lateinit var btnSave: MaterialButton

  private lateinit var vmUserDetail: UserDetailViewModel

  private var siswa: PenggunaModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_detail_unver)

    if (intent.getParcelableExtra<PenggunaModel>(UserListFragment.UNVERIFIED_USER) != null) {
      siswa = intent.getParcelableExtra(UserListFragment.UNVERIFIED_USER)
    }

    tvName = findViewById(R.id.tv_user_detail_unver_nama)
    tvEmail = findViewById(R.id.tv_user_detail_unver_email)
    tvPasswd = findViewById(R.id.tv_user_detail_unver_passwd)

    if (siswa != null) {
      tvName.text = siswa?.nama
      tvEmail.text = siswa?.email
      tvPasswd.transformationMethod = PasswordTransformationMethod()
      tvPasswd.text = siswa?.passwd
    }

    setupAppBar()
    setupButtons()
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

  private fun setupButtons() {
    btnProofPay = findViewById(R.id.btn_user_detail_unver_buktibayar)
    btnProofPay.setOnClickListener {
      if (siswa != null) {
        val intent = Intent(this@UserDetailUnverActivity, ImagePreviewActivity::class.java)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE_TYPE, ImagePreviewActivity.IMG_URL)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE, siswa?.detailPengguna?.fotoBayarAwal!!)
        startActivity(intent)
      }
    }

    btnCancel = findViewById(R.id.btn_user_detail_unver_batal)
    btnCancel.setOnClickListener {
      onBackPressed()
    }

    btnSave = findViewById(R.id.btn_user_detail_unver_simpan)
    btnSave.setOnClickListener {
      val alertDialog = AlertDialogFragment(
        "Konfirmasi verifikasi",
        "Apakah Anda yakin melanjutkan verifikasi?"
      )
      alertDialog.show(supportFragmentManager, null)
    }
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

  override fun alertAction(tag: String?) {
    vmUserDetail.updateDataToVerified(siswa!!)
  }
}