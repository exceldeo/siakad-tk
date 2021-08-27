package app.siakad.siakadtkadmin.presentation.screens.classroom.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.detail.ClassroomDetailViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.classroom.adapter.ClassroomDetailListAdapter
import app.siakad.siakadtkadmin.presentation.screens.classroom.detail.siswa.ClassroomSiswaDetailActivity
import app.siakad.siakadtkadmin.presentation.screens.classroom.helper.DetailClassClickHelper
import app.siakad.siakadtkadmin.presentation.screens.registration.RegistrationListFragment
import app.siakad.siakadtkadmin.presentation.screens.registration.detail.unverified.RegistrationDetailUnverActivity
import app.siakad.siakadtkadmin.presentation.screens.registration.detail.verified.RegistrationDetailActivity
import app.siakad.siakadtkadmin.presentation.screens.user.detail.verified.adapter.UserListAdapter

class ClassroomDetailActivity : AppCompatActivity(), DetailClassClickHelper {

  private var pageTitle = "Kelas"

  private lateinit var tvClassroomDetailCount: TextView
  private lateinit var rvClassroomDetail: RecyclerView

  private lateinit var vmClassroomDetail: ClassroomDetailViewModel

  private lateinit var svClassroomDetail: SearchView
  private lateinit var ivAddClassroomDetail: ImageView

  private lateinit var classroomDetailListAdapter: ClassroomDetailListAdapter

  private var kelas = KelasModel()

  companion object {
    const val CLASSROOM_ID = "classroom_id"
    const val DATA_SISWA = "data_siswa"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_classroom_detail)

    kelas = intent.getParcelableExtra(CLASSROOM_ID)
    pageTitle = kelas.namaKelas

    tvClassroomDetailCount = findViewById(R.id.tv_classroom_detail_jumlah_siswa)
    svClassroomDetail = findViewById(R.id.sv_classroom_detail_cari)

    setupAppBar()
    setupButtons()
    setupAdapter()
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

  private fun setupAppBar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
    setSupportActionBar(toolbar)
    supportActionBar?.title = pageTitle
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun setupButtons() {
//    ivAddClassroomDetail = findViewById(R.id.btn_classroom_detail_tambah_siswa)
//    ivAddClassroomDetail.setOnClickListener {
//    }
  }

  private fun setupViewModel() {
    vmClassroomDetail = ViewModelProvider(
      this, ViewModelFactory(this, this)
    ).get(ClassroomDetailViewModel::class.java)
    vmClassroomDetail.setUsers(kelas.daftarSiswa)

    val obsClassroomDetail = Observer<ArrayList<PenggunaModel>> { newUserList ->
      if (newUserList.size > 0) {
        classroomDetailListAdapter.changeDataList(newUserList)
      }
    }
    vmClassroomDetail.getUserList()
      .observe(this, obsClassroomDetail)
  }

  private fun setupAdapter() {
    rvClassroomDetail = findViewById(R.id.rv_classroom_detail_daftar_siswa)
    classroomDetailListAdapter = ClassroomDetailListAdapter()
    rvClassroomDetail.apply {
      setHasFixedSize(true)
      adapter = classroomDetailListAdapter
      layoutManager = LinearLayoutManager(this.context)
    }
  }

  override fun navigateToClassroomDetailDetail(siswa: PenggunaModel) {
      val intent = Intent(this, ClassroomSiswaDetailActivity::class.java)
      intent.putExtra(DATA_SISWA, siswa)
      startActivity(intent)
  }
}
