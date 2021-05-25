package app.siakad.siakadtkadmin.presentation.screens.announcement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.classroom.ClassroomListFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener
import app.siakad.siakadtkadmin.presentation.views.date.DateListener
import app.siakad.siakadtkadmin.presentation.views.date.DatePickerFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.text.SimpleDateFormat
import java.util.*

class AnnouncementDetailActivity : AppCompatActivity(), DateListener, AlertListener {

  private val pageTitle = "Ubah Pengumuman"

  private lateinit var etTitle: EditText
  private lateinit var etContent: EditText
  private lateinit var ivDate: ImageView
  private lateinit var etDate: EditText
  
  private lateinit var tvSiswaName: TextView
  private lateinit var tvIsConfirmed: TextView

  private lateinit var btnDelete: MaterialButton
  private lateinit var btnCancel: MaterialButton
  private lateinit var btnSave: MaterialButton

  private lateinit var datePicker: DatePickerFragment
  private lateinit var calendar: Calendar

  private lateinit var vmAnnouncementAdd: AnnouncementAddViewModel

  private var pengumuman: PengumumanModel? = null
  private var announcementType: String = AnnouncementListFragment.TO_SISWA

  private var tujuanId: String? = null

  companion object {
    const val ANNOUNCEMENT_EDIT = "edit_pengumuman"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_announcement_detail)

    if (intent.getParcelableExtra<PengumumanModel>(ANNOUNCEMENT_EDIT) != null) {
      pengumuman = intent.getParcelableExtra(ANNOUNCEMENT_EDIT)
    }

    etTitle = findViewById(R.id.et_announcement_detail_judul)
    etContent = findViewById(R.id.et_announcement_detail_isi)
    tvSiswaName = findViewById(R.id.tv_announcement_detail_siswa)
    tvIsConfirmed = findViewById(R.id.tv_announcement_detail_status_konfirm)

    datePicker = DatePickerFragment()
    calendar = Calendar.getInstance()

    if (pengumuman != null) {
      btnDelete = findViewById(R.id.btn_announcement_detail_hapus)

      announcementType = pengumuman?.tipe!!
      etTitle.setText(pengumuman?.judul)
      etContent.setText(pengumuman?.keterangan)
      tujuanId = pengumuman?.tujuanId

      if (pengumuman?.confirmable!!) {
        if (pengumuman?.confirmableState!!) {
          tvIsConfirmed.text = "Sudah Dikonfirmasi"
        } else {
          tvIsConfirmed.text = "Belum Dikonfirmasi"
        }
      }
    }

    setupAppBar()
    setupViewModel()
    setupButtons()

    setupDate()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        navigateBack()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onDataSet(year: Int, month: Int, day: Int, tag: String) {
    calendar.set(year, month, day)
    setupDate()
  }

  private fun setupAppBar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
    setSupportActionBar(toolbar)
    supportActionBar?.title = pageTitle
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun setupViewModel() {
    vmAnnouncementAdd = ViewModelProvider(
      this,
      ViewModelFactory(
        this,
        this
      )
    ).get(AnnouncementAddViewModel::class.java)

    if (pengumuman != null) {
      if (pengumuman?.tujuanId != "") {
        vmAnnouncementAdd.getUser(pengumuman?.tujuanId!!)

        val userByIdObserver = Observer<PenggunaModel> { user ->
          tvSiswaName.text = user.nama
        }

        vmAnnouncementAdd.getUserById().observe(this, userByIdObserver)
      }
    }
  }

  private fun setupButtons() {
    ivDate = findViewById(R.id.iv_announcement_detail_tanggal)
    ivDate.setOnClickListener {
      val arg = Bundle()

      arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
      arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
      arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
      datePicker.arguments = arg

      datePicker.show(supportFragmentManager, null)
    }

    etDate = findViewById(R.id.et_announcement_detail_tanggal)
    if (pengumuman != null) {
      etDate.setText(pengumuman?.tanggal)
    }
    etDate.setOnClickListener {
      val arg = Bundle()

      arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
      arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
      arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
      datePicker.arguments = arg

      datePicker.show(supportFragmentManager, null)
    }

    btnCancel = findViewById(R.id.btn_announcement_detail_batal)
    btnCancel.setOnClickListener {
      navigateBack()
    }

    btnSave = findViewById(R.id.btn_announcement_detail_simpan)
    if (pengumuman != null) {
      btnSave.text = "Simpan"
    }
    btnSave.setOnClickListener {
      if (validateInput()) {
        if (pengumuman != null) {
          vmAnnouncementAdd.updateAnnouncement(
            etTitle.text.toString(),
            etContent.text.toString(),
            etDate.text.toString(),
            announcementType,
            tujuanId,
            pengumuman!!,
            true
          )
        }
      }
    }

    if (pengumuman != null) {
      btnDelete.setOnClickListener {
        val alertDialog = AlertDialogFragment(
          "Hapus pengumuman",
          "Apakah Anda yakin menghapus pesanan ini?"
        )
        alertDialog.show(supportFragmentManager, null)
      }
    }
  }

  private fun setupDate() {
    val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
    etDate.text = SpannableStringBuilder(date)
  }

  private fun navigateBack() {
    onBackPressed()
  }

  private fun validateInput(): Boolean {
    var returnState = true

    if (etTitle.text.isEmpty()) {
      etTitle.error = getString(R.string.empty_input)
      returnState = false
    }

    if (etContent.text.isEmpty()) {
      etContent.error = getString(R.string.empty_input)
      returnState = false
    }

    if (etDate.text.isEmpty()) {
      etDate.error = getString(R.string.empty_input)
      returnState = false
    }

    return returnState
  }

  override fun alertAction(tag: String?) {
    vmAnnouncementAdd.removeData(pengumuman!!)
    onBackPressed()
  }
}