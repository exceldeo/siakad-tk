package app.siakad.siakadtk.ui.announcement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.data.model.Announcement

class AnnouncementListActivity : AppCompatActivity() {

    private lateinit var rvAnnouncement: RecyclerView
    private var listAnnouncement: ArrayList<Announcement> = arrayListOf()
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_list)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        rvAnnouncement = findViewById(R.id.rv_announcement_daftar_pengumuman)
        btnBack = findViewById(R.id.btn_announcement_back)
        rvAnnouncement.setHasFixedSize(true)
        listAnnouncement.addAll(AnnouncementsData.listData)
        showAnnouncementRecyclerList()
    }

    private fun setupView() {
        btnBack.setOnClickListener {
            
        }
    }

    private fun showAnnouncementRecyclerList() {
        rvAnnouncement.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val announcementAdapter = AnnouncementInsideAdapter(listAnnouncement)
        rvAnnouncement.adapter = announcementAdapter
    }
}