package app.siakad.siakadtk.presentation.screens.announcement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.PengumumanModel

class AnnouncementDetailActivity : AppCompatActivity() {

    private var pageTitle = ""

    private lateinit var toolbar: Toolbar
    private lateinit var tvAnnouncementTitle: TextView
    private lateinit var tvAnnouncementType: TextView
    private lateinit var tvAnnouncementDesc: TextView
    private lateinit var tvAnnouncementDate: TextView
    private lateinit var cvConfirm: CardView
    private lateinit var btnYesConfirm: TextView
    private lateinit var btnNoConfirm: TextView
    private var item = PengumumanModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_detail)

        pageTitle = "Detail Pengumuman"
        setupAppBar()
        setupItemView()

        val data = intent.getParcelableExtra<Parcelable>("pengumuman") as PengumumanModel
        item = data
        tvAnnouncementTitle.text = item.judul
        tvAnnouncementType.text = item.tipe
        tvAnnouncementDesc.text = item.keterangan
        tvAnnouncementDate.text = item.tanggal

        setupView()
    }

    private fun setupView() {
        btnYesConfirm.setOnClickListener {

        }
        btnNoConfirm.setOnClickListener {

        }
        if (item.confirmable) cvConfirm.visibility = View.VISIBLE
        else cvConfirm.visibility = View.GONE
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvAnnouncementTitle = findViewById(R.id.tv_announcement_detail_title)
        tvAnnouncementType = findViewById(R.id.tv_announcement_detail_type)
        tvAnnouncementDesc = findViewById(R.id.tv_announcement_detail_desc)
        tvAnnouncementDate = findViewById(R.id.tv_announcement_detail_tgl)

        btnYesConfirm = findViewById(R.id.btn_announcement_detail_ya)
        btnNoConfirm = findViewById(R.id.btn_announcement_detail_tidak)

        cvConfirm = findViewById(R.id.cv_announcement_confirm)
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}