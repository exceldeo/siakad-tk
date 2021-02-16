package app.siakad.siakadtkadmin.ui.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.announcement.AnnouncementAddActivity

class NotificationActivity : AppCompatActivity() {

    private lateinit var svNotif: SearchView
    private lateinit var svNumNotif: TextView
    private lateinit var ivAddNotif: ImageView
    private lateinit var rvNotif: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        svNotif = findViewById(R.id.sv_notification_cari)
        svNumNotif = findViewById(R.id.tv_notification_jumlah_notif)
        ivAddNotif = findViewById(R.id.iv_notification_tambah_notif)
        rvNotif = findViewById(R.id.rv_notification_daftar_notif)
    }

    private fun setupView() {
        ivAddNotif.setOnClickListener{
            val intent = Intent(this@NotificationActivity, NotificationAddActivity::class.java)
            startActivity(intent)
        }
    }
}