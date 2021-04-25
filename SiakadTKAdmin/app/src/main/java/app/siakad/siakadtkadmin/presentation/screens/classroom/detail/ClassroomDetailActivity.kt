package app.siakad.siakadtkadmin.presentation.screens.classroom.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.detail.ClassroomDetailViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.announcement.AnnouncementActivity
import app.siakad.siakadtkadmin.presentation.screens.classroom.ClassroomActivity
import app.siakad.siakadtkadmin.presentation.screens.user.detail.verified.adapter.UserListAdapter

class ClassroomDetailActivity : AppCompatActivity() {

    private var pageTitle = "Kelas"

    private lateinit var tvClassroomDetailCount: TextView
    private lateinit var rvClassroomDetail: RecyclerView

    private lateinit var vmClassroomDetail: ClassroomDetailViewModel

    private lateinit var svClassroomDetail: SearchView
    private lateinit var ivAddClassroomDetail: ImageView

    private lateinit var userListAdapter: UserListAdapter

    private val kelas = intent.getParcelableExtra<KelasModel>(CLASSROOM_ID)

    companion object {
        const val CLASSROOM_ID = "classroom_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom_detail)

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
                navigateBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAppBar() {
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupButtons() {
        ivAddClassroomDetail = findViewById(R.id.btn_classroom_detail_tambah_siswa)
        ivAddClassroomDetail.setOnClickListener {
        }
    }

    private fun setupViewModel() {
        vmClassroomDetail = ViewModelProvider(
            this, ViewModelFactory(this, this)
        ).get(ClassroomDetailViewModel::class.java)
        vmClassroomDetail.setUserClassType(kelas.kelasId)

        val obsClassroomDetail = Observer<ArrayList<Siswa>> { newUserList ->
            if (newUserList.size > 0) {
                userListAdapter.changeDataList(newUserList)
            }
        }
        vmClassroomDetail.getUserList()
            .observe(this, obsClassroomDetail)
    }

    private fun setupAdapter() {
        rvClassroomDetail = findViewById(R.id.rv_classroom_detail_daftar_siswa)
        userListAdapter = UserListAdapter()
        rvClassroomDetail.apply {
            setHasFixedSize(true)
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@ClassroomDetailActivity,
                ClassroomActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}