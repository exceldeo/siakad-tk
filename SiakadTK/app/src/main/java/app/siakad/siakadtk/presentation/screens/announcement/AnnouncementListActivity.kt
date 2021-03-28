package app.siakad.siakadtk.presentation.screens.announcement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtk.presentation.screens.announcement.inside.adapter.AnnouncementInsideAdapter
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory

class AnnouncementListActivity : AppCompatActivity() {

    private val pageTitle = "Pengumuman"

    private lateinit var toolbar: Toolbar
    private lateinit var rvAnnouncement: RecyclerView
    private lateinit var rvAnnouncementAdapter: AnnouncementInsideAdapter

    private lateinit var vmAnnouncement: AnnouncementViewModel
    private lateinit var announcementListObserver: Observer<ArrayList<Pengumuman>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_list)
        setupItemView()
        setupView()
        setupObserver()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        rvAnnouncement = findViewById(R.id.rv_announcement_daftar_pengumuman)
        rvAnnouncement.setHasFixedSize(true)
        rvAnnouncementAdapter = AnnouncementInsideAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@AnnouncementListActivity,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        setupAppBar()

        rvAnnouncement.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AnnouncementListActivity)
            adapter = rvAnnouncementAdapter
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
                rvAnnouncementAdapter.changeDataList(list)
            }
        }

        vmAnnouncement.getAnnouncementList().observe(this, announcementListObserver)
    }
}