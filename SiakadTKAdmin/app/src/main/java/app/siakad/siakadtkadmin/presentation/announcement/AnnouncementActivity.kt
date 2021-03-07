package app.siakad.siakadtkadmin.presentation.announcement

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
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.presentation.announcement.adapter.AnnouncementAdater
import app.siakad.siakadtkadmin.presentation.announcement.add.AnnouncementAddActivity
import app.siakad.siakadtkadmin.presentation.main.MainActivity
import app.siakad.siakadtkadmin.presentation.utils.factory.ViewModelFactory

class AnnouncementActivity : AppCompatActivity() {

    private val pageTitle = "Pengumuman"

    private lateinit var toolbar: Toolbar
    private lateinit var svAnnounc: SearchView
    private lateinit var tvNumAnnounc: TextView
    private lateinit var ivAddAnnounc: ImageView
    private lateinit var rvAnnounc: RecyclerView
    private lateinit var rvAnnouncAdapter: AnnouncementAdater

    private lateinit var vmAnnouncement: AnnouncementViewModel
    private lateinit var announcementListObserver: Observer<ArrayList<PengumumanModel>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        setupItemView()
        setupView()
        setupObserver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@AnnouncementActivity,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        svAnnounc = findViewById(R.id.sv_announcement_cari)
        tvNumAnnounc = findViewById(R.id.tv_announcement_jumlah_pengumuman)
        ivAddAnnounc = findViewById(R.id.iv_announcement_tambah_announ)
        rvAnnounc = findViewById(R.id.rv_announcement_daftar_pengumuman)
        rvAnnouncAdapter = AnnouncementAdater()
    }

    private fun setupView() {
        setupAppBar()

        ivAddAnnounc.setOnClickListener {
            val intent = Intent(this@AnnouncementActivity, AnnouncementAddActivity::class.java)
            startActivity(intent)
        }

        rvAnnounc.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AnnouncementActivity)
            adapter = rvAnnouncAdapter
        }

        vmAnnouncement = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(AnnouncementViewModel::class.java)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObserver() {
        announcementListObserver = Observer { list ->
            if (list.size > 0) {
                rvAnnouncAdapter.changeDataList(list)
            }
        }

        vmAnnouncement.getAnnouncementList().observe(this, announcementListObserver)
    }
}