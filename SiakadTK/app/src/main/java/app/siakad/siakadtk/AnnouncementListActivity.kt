package app.siakad.siakadtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

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