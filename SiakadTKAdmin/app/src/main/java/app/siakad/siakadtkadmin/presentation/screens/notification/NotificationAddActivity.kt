package app.siakad.siakadtkadmin.presentation.screens.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.notification.NotificationAddViewModel
import app.siakad.siakadtkadmin.presentation.views.date.DateListener
import app.siakad.siakadtkadmin.presentation.views.date.DatePickerFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationAddActivity : AppCompatActivity(), DateListener {

    private val pageTitle = "Tambah Notifikasi"

    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var ivDate: ImageView
    private lateinit var etDate: EditText
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView
    private lateinit var ddReceiver: TextInputLayout

    private lateinit var atvSiswa: AutoCompleteTextView
    private lateinit var atvKelas: AutoCompleteTextView

    private lateinit var layoutSiswa: LinearLayout
    private lateinit var layoutKelas: LinearLayout

    private lateinit var siswaListAdapter: ArrayAdapter<Siswa>
    private lateinit var kelasListAdapter: ArrayAdapter<Siswa>

    private lateinit var datePicker: DatePickerFragment
    private lateinit var calendar: Calendar

    private lateinit var vmNotificationAdd: NotificationAddViewModel
    private lateinit var userObserver: Observer<ArrayList<Siswa>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_add)

        etTitle = findViewById(R.id.et_notification_add_judul)
        etContent = findViewById(R.id.et_notification_add_isi)

        datePicker = DatePickerFragment()
        calendar = Calendar.getInstance()

        setupAppBar()
        setupViewModel()
        setupAutoCompleteView()
        setupDropDown()
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

    override fun onDataSet(year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        setupDate()
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAutoCompleteView() {
        atvSiswa = findViewById(R.id.et_notification_add_nama_siswa)
        siswaListAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayListOf())
        atvSiswa.setAdapter(siswaListAdapter)
        atvSiswa.setOnItemClickListener { adapter, _, position, _ ->
            val siswa: Siswa = adapter.getItemAtPosition(position) as Siswa
            showToast(siswa.nama)
        }
        atvSiswa.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(str: Editable?) {}

            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        atvKelas = findViewById(R.id.et_notification_add_nama_kelas)
        kelasListAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayListOf())
        atvKelas.setAdapter(kelasListAdapter)
        atvKelas.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(str: Editable?) {}

            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupViewModel() {
        vmNotificationAdd = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(NotificationAddViewModel::class.java)

        userObserver = Observer {userList ->
            siswaListAdapter.addAll(userList)
            atvSiswa.setAdapter(siswaListAdapter)
        }

        vmNotificationAdd.getUserList().observe(this, userObserver)
    }

    private fun setupDropDown() {
        val menus = arrayListOf<String>(
            "Semua",
            "Siswa",
            "Kelas"
        )
        val adapter = ArrayAdapter(this.applicationContext, R.layout.item_dropdown, menus)

        layoutSiswa = findViewById(R.id.ll_notification_add_siswa)
        layoutSiswa.visibility = View.GONE
        layoutKelas = findViewById(R.id.ll_notification_add_kelas)
        layoutKelas.visibility = View.GONE

        ddReceiver = findViewById(R.id.dd_notification_add)
        (ddReceiver.editText as MaterialAutoCompleteTextView).setText(menus[0])
        (ddReceiver.editText as MaterialAutoCompleteTextView).setAdapter(adapter)
        (ddReceiver.editText as MaterialAutoCompleteTextView).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(str: Editable?) {
                if (str.toString().equals(menus[0])) {
                    layoutKelas.visibility = View.GONE
                    layoutSiswa.visibility = View.GONE
                } else if (str.toString().equals(menus[1])) {
                    layoutKelas.visibility = View.GONE
                    layoutSiswa.visibility = View.VISIBLE
                } else {
                    layoutKelas.visibility = View.VISIBLE
                    layoutSiswa.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupButtons() {
        ivDate = findViewById(R.id.iv_notification_add_tanggal)
        ivDate.setOnClickListener {
            val arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }

        etDate = findViewById(R.id.et_notification_add_tanggal)
        etDate.setOnClickListener {
            val arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }

        btnCancel = findViewById(R.id.btn_notification_add_batal)
        btnCancel.setOnClickListener {
            navigateBack()
        }

        btnSave = findViewById(R.id.btn_notification_add_simpan)
        btnSave.setOnClickListener {
            if (validateInput()) {
                vmNotificationAdd.setData(
                    etTitle.text.toString(),
                    etContent.text.toString(),
                    etDate.text.toString()
                )
            }
        }
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

        if (layoutSiswa.visibility == View.VISIBLE) {
            if (atvSiswa.text.isEmpty()) {
                atvSiswa.error = getString(R.string.empty_input)
                returnState = false
            }
        }

        if (layoutKelas.visibility == View.VISIBLE) {
            if (atvKelas.text.isEmpty()) {
                atvKelas.error = getString(R.string.empty_input)
                returnState = false
            }
        }

        return returnState
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}