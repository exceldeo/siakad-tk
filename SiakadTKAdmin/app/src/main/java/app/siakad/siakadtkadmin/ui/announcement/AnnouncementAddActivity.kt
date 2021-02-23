package app.siakad.siakadtkadmin.ui.announcement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.main.MainActivity
import app.siakad.siakadtkadmin.ui.view.date.DatePickerHelper

class AnnouncementAddActivity : AppCompatActivity() {

    private val pageTitle = "Tambah Pengumuman"

    private lateinit var toolbar: Toolbar
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var ivDate: ImageView
    private lateinit var etDate: EditText
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    private lateinit var datePicker: DatePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_add)

        setupItemView()
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@AnnouncementAddActivity, AnnouncementActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        etTitle = findViewById(R.id.et_announcement_add_judul)
        etContent = findViewById(R.id.et_announcement_add_isi)
        ivDate = findViewById(R.id.iv_announcement_add_tanggal)
        etDate = findViewById(R.id.et_announcement_add_tanggal)
        btnCancel = findViewById(R.id.btn_announcement_add_batal)
        btnSave = findViewById(R.id.btn_announcement_add_simpan)

        datePicker = DatePickerHelper(supportFragmentManager)
    }

    private fun setupView() {
        setupAppBar()

        ivDate.setOnClickListener {
            datePicker.showDialog()
        }
        etDate.setOnClickListener {
            datePicker.showDialog()
        }
    }

    private fun setupAppBar() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObserver() {
    }

    private fun initRVADataList() {
    }
}