package app.siakad.siakadtk.presentation.screens.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile.ProfileViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.views.date.DateListener
import app.siakad.siakadtk.presentation.views.date.DatePickerFragment
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, DateListener {
    private val pageTitle = "Profil Siswa"

    private lateinit var toolbar: Toolbar
    private lateinit var civProfileImg: CircleImageView
    private lateinit var btnChangeProfileImg: TextView
    private lateinit var etName: EditText
    private lateinit var spClass: Spinner
    private lateinit var spTahunAjaran: Spinner
    private lateinit var spGender: Spinner
    private lateinit var etBornDate: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etParentName: EditText
    private lateinit var btnCancel: TextView
    private lateinit var btnSimpan: TextView

    private lateinit var datePicker: DatePickerFragment
    private lateinit var calendar: Calendar

    private lateinit var vmProfile : ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
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

    private fun setupView() {
        setupAppBar()
        etBornDate.setOnClickListener {
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

        btnSimpan.setOnClickListener {
            if (validateInput()) {
                vmProfile.setData(
                    etName.text.toString(),
                    spClass.selectedItem.toString(),
                    etParentName.text.toString(),
                    spTahunAjaran.selectedItem.toString(),
                    spGender.selectedItem.toString(),
                    etBornDate.text.toString(),
                    etAddress.text.toString(),
                    etPhoneNumber.text.toString()
                )
                navigateBack()
            }
        }

        vmProfile = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(ProfileViewModel::class.java)
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        civProfileImg = findViewById(R.id.civ_aprofile_user_img)
        btnChangeProfileImg = findViewById(R.id.btn_aprofile_ubah_foto)
        etName = findViewById(R.id.et_aprofile_nama)
        spClass = findViewById(R.id.sp_aprofile_kelas)
        spTahunAjaran = findViewById(R.id.sp_aprofile_tahunajaran)
        spGender = findViewById(R.id.sp_aprofile_jeniskelamin)
        etBornDate = findViewById(R.id.et_aprofile_ttl)
        etAddress = findViewById(R.id.et_aprofile_alamat)
        etPhoneNumber = findViewById(R.id.et_aprofile_no_hp_ortu)
        etParentName = findViewById(R.id.et_aprofile_nama_ortu)
        btnCancel = findViewById(R.id.btn_aprofile_batal)
        btnSimpan = findViewById(R.id.btn_aprofile_simpan)

        datePicker = DatePickerFragment()
        calendar = Calendar.getInstance()

        setupAdapterListener()
        spGender.onItemSelectedListener = this
        spClass.onItemSelectedListener = this
        spTahunAjaran.onItemSelectedListener = this
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupDate() {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        etBornDate.text = SpannableStringBuilder(date)
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@ProfileActivity,
                SettingsActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    private fun setupAdapterListener() {
        ArrayAdapter.createFromResource(
            this, R.array.list_jenis_kelamin,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spGender.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this, R.array.list_kelas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spClass.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this, R.array.list_ajaran,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spTahunAjaran.adapter = adapter
        }
    }

    private fun validateInput(): Boolean {
        var returnState = true

//        showToast(spGender.selectedItem.toString() + " " + spClass.selectedItem.toString() + " " + spTahunAjaran.selectedItem.toString())

        if (etName.text.isEmpty()) {
            etName.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etBornDate.text.isEmpty()) {
            etBornDate.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etParentName.text.isEmpty()) {
            etParentName.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etAddress.text.isEmpty()) {
            etAddress.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etPhoneNumber.text.isEmpty()) {
            etPhoneNumber.error = getString(R.string.empty_input)
            returnState = false
        }

        return returnState
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDataSet(year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        setupDate()
    }
}