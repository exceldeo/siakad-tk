package app.siakad.siakadtkadmin.presentation.announcement

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.main.MainActivity

class AnnouncementActivity : AppCompatActivity() {

    private val pageTitle = "Pengumuman"

    private lateinit var toolbar: Toolbar
    private lateinit var svAnnounce: SearchView
    private lateinit var tvNumAnnounce: TextView
    private lateinit var ivAddAnnounce: ImageView
    private lateinit var rvAnnounce: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        setupItemView()
        setupView()
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
        svAnnounce = findViewById(R.id.sv_announcement_cari)
        tvNumAnnounce = findViewById(R.id.tv_announcement_jumlah_pengumuman)
        ivAddAnnounce = findViewById(R.id.iv_announcement_tambah_announ)
        rvAnnounce = findViewById(R.id.rv_announcement_daftar_pengumuman)
    }

    private fun setupView() {
        setupAppBar()

        ivAddAnnounce.setOnClickListener{
            val intent = Intent(this@AnnouncementActivity, AnnouncementAddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}