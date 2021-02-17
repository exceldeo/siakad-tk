package app.siakad.siakadtk.ui.announcement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class AnnouncementListActivity : AppCompatActivity() {

    private lateinit var rvAnnouncement: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_list)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        rvAnnouncement = findViewById(R.id.rv_announcement_daftar_pengumuman)
    }

    private fun setupView() {

    }
}