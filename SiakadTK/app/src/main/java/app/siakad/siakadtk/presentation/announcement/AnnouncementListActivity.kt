package app.siakad.siakadtk.presentation.announcement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.main.MainActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.presentation.announcement.inside.adapter.AnnouncementInsideAdapter

class AnnouncementListActivity : AppCompatActivity() {

    private val pageTitle = "Pengumuman"

    private lateinit var toolbar: Toolbar
    private lateinit var rvAnnouncement: RecyclerView
    private var listAnnouncement: ArrayList<PengumumanModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_list)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        rvAnnouncement = findViewById(R.id.rv_announcement_daftar_pengumuman)
        rvAnnouncement.setHasFixedSize(true)
        listAnnouncement.addAll(AnnouncementsData.listData)
        showAnnouncementRecyclerList()
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
    }

    private fun showAnnouncementRecyclerList() {
        rvAnnouncement.layoutManager = LinearLayoutManager(this)
        val announcementAdapter = AnnouncementInsideAdapter(listAnnouncement)
        rvAnnouncement.adapter = announcementAdapter
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}