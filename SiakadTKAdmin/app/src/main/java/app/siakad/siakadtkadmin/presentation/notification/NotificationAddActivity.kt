package app.siakad.siakadtkadmin.presentation.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.viewmodels.factory.ViewModelFactory
import app.siakad.siakadtkadmin.infrastructure.viewmodels.notification.NotificationAddViewModel
import app.siakad.siakadtkadmin.presentation.views.date.DateListener
import app.siakad.siakadtkadmin.presentation.views.date.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class NotificationAddActivity : AppCompatActivity(), DateListener {

    private val pageTitle = "Tambah Notifikasi"

    private lateinit var toolbar: Toolbar
    private lateinit var etUser: EditText
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var ivDate: ImageView
    private lateinit var etDate: EditText
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    private lateinit var datePicker: DatePickerFragment
    private lateinit var calendar: Calendar

    private lateinit var vmNotificationAdd: NotificationAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_add)

        setupItemView()
        setupView()
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

    override fun onDataSet(year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        setupDate()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        etUser = findViewById(R.id.et_notification_add_nama)
        etTitle = findViewById(R.id.et_notification_add_judul)
        etContent = findViewById(R.id.et_notification_add_isi)
        ivDate = findViewById(R.id.iv_notification_add_tanggal)
        etDate = findViewById(R.id.et_notification_add_tanggal)
        btnCancel = findViewById(R.id.btn_notification_add_batal)
        btnSave = findViewById(R.id.btn_notification_add_simpan)
        datePicker = DatePickerFragment()
        calendar = Calendar.getInstance()
    }

    private fun setupView() {
        setupAppBar()
        setupDate()

        ivDate.setOnClickListener {
            var arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }
        etDate.setOnClickListener {
            var arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }

        btnCancel.setOnClickListener {
            navigateBack()
        }

        btnSave.setOnClickListener {
            if (validateInput()) {
                vmNotificationAdd.setData(
                    etTitle.text.toString(),
                    etContent.text.toString(),
                    etDate.text.toString()
                )
            }
        }

        vmNotificationAdd = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(NotificationAddViewModel::class.java)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupDate() {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        etDate.text = SpannableStringBuilder(date)
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@NotificationAddActivity,
                NotificationActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
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
}