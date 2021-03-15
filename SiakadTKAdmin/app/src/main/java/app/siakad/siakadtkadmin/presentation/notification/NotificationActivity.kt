package app.siakad.siakadtkadmin.presentation.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Notifikasi
import app.siakad.siakadtkadmin.infrastructure.viewmodels.notification.NotificationViewModel
import app.siakad.siakadtkadmin.presentation.main.MainActivity
import app.siakad.siakadtkadmin.presentation.notification.adapter.NotificationAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.siakad.siakadtkadmin.infrastructure.viewmodels.factory.ViewModelFactory
import kotlin.collections.ArrayList

class NotificationActivity : AppCompatActivity() {

    private val pageTitle = "Notifikasi"

    private lateinit var toolbar: Toolbar
    private lateinit var svNotif: SearchView
    private lateinit var svNumNotif: TextView
    private lateinit var ivAddNotif: ImageView

    private lateinit var rvNotif: RecyclerView
    private lateinit var rvNotifAdapter: NotificationAdapter

    private lateinit var vmNotification: NotificationViewModel
    private lateinit var notificationObserver: Observer<ArrayList<Notifikasi>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        setupItemView()
        setupView()
        setupObserver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@NotificationActivity,
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
        svNotif = findViewById(R.id.sv_notification_cari)
        svNumNotif = findViewById(R.id.tv_notification_jumlah_notif)
        ivAddNotif = findViewById(R.id.iv_notification_tambah_notif)

        rvNotif = findViewById(R.id.rv_notification_daftar_notif)
        rvNotifAdapter = NotificationAdapter()
    }

    private fun setupView() {
        setupAppBar()

        ivAddNotif.setOnClickListener {
            val intent = Intent(this@NotificationActivity, NotificationAddActivity::class.java)
            startActivity(intent)
        }

        rvNotif.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = rvNotifAdapter
        }

        vmNotification = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(NotificationViewModel::class.java)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObserver() {
        notificationObserver = Observer { list ->
            if (list.size > 0) {
                rvNotifAdapter.changeDataList(list)
            }
        }

        vmNotification.getNotificationList().observe(this, notificationObserver)
    }
}