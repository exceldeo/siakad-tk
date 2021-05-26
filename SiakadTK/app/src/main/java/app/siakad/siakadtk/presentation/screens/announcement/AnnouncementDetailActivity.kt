package app.siakad.siakadtk.presentation.screens.announcement
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.registration.RegistrationActivity
import app.siakad.siakadtk.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtk.presentation.views.alert.AlertListener

class AnnouncementDetailActivity : AppCompatActivity(), AlertListener {

    private var pageTitle = ""

    private lateinit var toolbar: Toolbar
    private lateinit var tvAnnouncementTitle: TextView
    private lateinit var tvAnnouncementType: TextView
    private lateinit var tvAnnouncementDesc: TextView
    private lateinit var tvAnnouncementDate: TextView
    private lateinit var cvConfirm: CardView
    private lateinit var llConfirmState: LinearLayout
    private lateinit var tvConfirmState: TextView
    private lateinit var btnYesConfirm: TextView
    private lateinit var btnNoConfirm: TextView
    private var item = PengumumanModel()

    private lateinit var vmAnnouncement: AnnouncementViewModel

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
        if (item.confirmable) {
            llConfirmState.visibility = View.VISIBLE
            tvConfirmState.text = item.confirmableState.toString()

            if(!item.confirmableState) cvConfirm.visibility = View.VISIBLE
            else cvConfirm.visibility = View.GONE
        }
        else {
            cvConfirm.visibility = View.GONE
            llConfirmState.visibility = View.GONE
        }

        btnYesConfirm.setOnClickListener {
            val alertDialog = AlertDialogFragment(
                "Konfirmasi Pengumuman",
                "Apakah Anda yakin mengonfirmasi \"Iya\"?"
            )
            alertDialog.show(supportFragmentManager, "YA")
        }
        btnNoConfirm.setOnClickListener {
            navigateBack()
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvAnnouncementTitle = findViewById(R.id.tv_announcement_detail_title)
        tvAnnouncementType = findViewById(R.id.tv_announcement_detail_type)
        tvAnnouncementDesc = findViewById(R.id.tv_announcement_detail_desc)
        tvAnnouncementDate = findViewById(R.id.tv_announcement_detail_tgl)

        btnYesConfirm = findViewById(R.id.btn_announcement_detail_ya)
        btnNoConfirm = findViewById(R.id.btn_announcement_detail_tidak)

        llConfirmState = findViewById(R.id.ll_announcement_state_view)
        tvConfirmState = findViewById(R.id.tv_announcement_detail_confirm_state)
        cvConfirm = findViewById(R.id.cv_announcement_confirm)

        vmAnnouncement = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(AnnouncementViewModel::class.java)
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

    override fun alertAction(tag: String?) {
        if(tag == "YA") {
            item.confirmableState = true
            vmAnnouncement.updateAnnouncement(true, item)
        }
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@AnnouncementDetailActivity,
                AnnouncementListActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}