package app.siakad.siakadtkadmin.presentation.screens.announcement

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
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.classroom.ClassroomListFragment
import app.siakad.siakadtkadmin.presentation.views.date.DateListener
import app.siakad.siakadtkadmin.presentation.views.date.DatePickerFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AnnouncementAddActivity : AppCompatActivity(), DateListener {

    private val pageTitle = "Tambah Pengumuman"

    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var ivDate: ImageView
    private lateinit var etDate: EditText
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnSave: MaterialButton

    private lateinit var ddReceiver: TextInputLayout
    private lateinit var ddReceiverClass: TextInputLayout

    private lateinit var atvSiswa: AutoCompleteTextView

    private lateinit var layoutSiswa: LinearLayout
    private lateinit var layoutKelas: LinearLayout

    private lateinit var siswaListAdapter: ArrayAdapter<PenggunaModel>

    private lateinit var datePicker: DatePickerFragment
    private lateinit var calendar: Calendar

    private lateinit var vmAnnouncementAdd: AnnouncementAddViewModel

    private var pengumuman: PengumumanModel? = null
    private var announcementType: String = AnnouncementListFragment.TO_ALL

    private var siswaData: MutableMap<String, String> = mutableMapOf()

    private var classroomType: String = ClassroomListFragment.TK_A
    private var classroomData: MutableMap<String, String> = mutableMapOf()

    private var tujuanId: String? = null

    companion object {
        const val ANNOUNCEMENT_EDIT = "edit_pengumuman"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_add)

        if (intent.getParcelableExtra<PengumumanModel>(ANNOUNCEMENT_EDIT) != null) {
            pengumuman = intent.getParcelableExtra(ANNOUNCEMENT_EDIT)
        } else if (intent.getStringExtra(AnnouncementListFragment.ANNOUNCEMENT_TYPE) != null) {
            announcementType = intent.getStringExtra(AnnouncementListFragment.ANNOUNCEMENT_TYPE)
        }

        etTitle = findViewById(R.id.et_announcement_add_judul)
        etContent = findViewById(R.id.et_announcement_add_isi)
        datePicker = DatePickerFragment()
        calendar = Calendar.getInstance()

        if (pengumuman != null) {
            announcementType = pengumuman?.tipe!!
            etTitle.setText(pengumuman?.judul)
            etContent.setText(pengumuman?.keterangan)
            tujuanId = pengumuman?.tujuanId
        }

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
        atvSiswa = findViewById(R.id.et_announcement_add_nama_siswa)
        siswaListAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayListOf())
        atvSiswa.setAdapter(siswaListAdapter)
        atvSiswa.setOnItemClickListener { adapter, _, position, _ ->
            val siswa: PenggunaModel = adapter.getItemAtPosition(position) as PenggunaModel
            showToast(siswa.nama)
        }
        atvSiswa.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(str: Editable?) {
                tujuanId = siswaData[str.toString()]
            }

            override fun beforeTextChanged(
                str: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupViewModel() {
        vmAnnouncementAdd = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(AnnouncementAddViewModel::class.java)

        val userObserver = Observer<ArrayList<PenggunaModel>> { userList ->
            siswaListAdapter.addAll(userList)
            atvSiswa.setAdapter(siswaListAdapter)
            userList.forEach {
                siswaData.putAll(it.pairNameId())
            }
        }

        vmAnnouncementAdd.getUserList().observe(this, userObserver)

        val classObserver = Observer<ArrayList<KelasModel>> { classList ->
            classList.forEach {
                classroomData.putAll(it.pairNameId())
            }
        }

        vmAnnouncementAdd.getClassroomList().observe(this, classObserver)

        if (pengumuman != null) {
            if (pengumuman?.tipe == AnnouncementListFragment.TO_SISWA) {
                if (pengumuman?.tujuanId != "") {
                    vmAnnouncementAdd.getUser(pengumuman?.tujuanId!!)

                    val userByIdObserver = Observer<PenggunaModel> { user ->
                        atvSiswa.setText(user.nama)
                    }

                    vmAnnouncementAdd.getUserById().observe(this, userByIdObserver)
                }
            } else if (pengumuman?.tipe == AnnouncementListFragment.TO_KELAS) {
                if (pengumuman?.tujuanId != "") {
                    vmAnnouncementAdd.getClassroom(pengumuman?.tujuanId!!)

                    val classByIdObserver = Observer<KelasModel> { classroom ->
                        (ddReceiverClass.editText as MaterialAutoCompleteTextView).setText(classroom.namaKelas)
                    }

                    vmAnnouncementAdd.getClassroomById().observe(this, classByIdObserver)
                }
            }
        }
    }

    private fun setupDropDown() {
        val menus = arrayListOf(
            AnnouncementListFragment.TO_ALL,
            AnnouncementListFragment.TO_SISWA,
            AnnouncementListFragment.TO_KELAS
        )
        val adapter = ArrayAdapter(this.applicationContext, R.layout.item_dropdown, menus)

        layoutSiswa = findViewById(R.id.ll_announcement_add_siswa)
        layoutKelas = findViewById(R.id.ll_announcement_add_kelas)
        checkMenu(announcementType)

        ddReceiver = findViewById(R.id.dd_announcement_add)
        (ddReceiver.editText as MaterialAutoCompleteTextView).setText(announcementType)
        (ddReceiver.editText as MaterialAutoCompleteTextView).setAdapter(adapter)
        (ddReceiver.editText as MaterialAutoCompleteTextView).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(str: Editable?) {
                checkMenu(str.toString())
            }

            override fun beforeTextChanged(
                str: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val classMenus = arrayListOf(
            ClassroomListFragment.TK_A,
            ClassroomListFragment.TK_B
        )
        val classAdapter = ArrayAdapter(this.applicationContext, R.layout.item_dropdown, classMenus)

        if (pengumuman?.tipe == AnnouncementListFragment.TO_KELAS) {
            if (pengumuman?.tujuanId != "") {
                atvSiswa.setText(pengumuman?.tujuanId!!)
            }
        }

        ddReceiverClass = findViewById(R.id.dd_announcement_add_nama_kelas)
        (ddReceiverClass.editText as MaterialAutoCompleteTextView).setText(classroomType)
        (ddReceiverClass.editText as MaterialAutoCompleteTextView).setAdapter(classAdapter)
        (ddReceiverClass.editText as MaterialAutoCompleteTextView).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(str: Editable?) {
                classroomType = str.toString()
                tujuanId = classroomData.get(classroomType)
            }

            override fun beforeTextChanged(
                str: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupButtons() {
        ivDate = findViewById(R.id.iv_announcement_add_tanggal)
        ivDate.setOnClickListener {
            val arg = Bundle()

            arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
            arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
            arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
            datePicker.arguments = arg

            datePicker.show(supportFragmentManager, null)
        }

        etDate = findViewById(R.id.et_announcement_add_tanggal)
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

        btnCancel = findViewById(R.id.btn_announcement_add_batal)
        btnCancel.setOnClickListener {
            navigateBack()
        }

        btnSave = findViewById(R.id.btn_announcement_add_simpan)
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
                        pengumuman!!
                    )
                } else {
                    vmAnnouncementAdd.insertAnnouncement(
                        etTitle.text.toString(),
                        etContent.text.toString(),
                        etDate.text.toString(),
                        announcementType,
                        tujuanId
                    )
                }
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

        if (layoutSiswa.visibility == View.VISIBLE) {
            if (atvSiswa.text.isEmpty()) {
                atvSiswa.error = getString(R.string.empty_input)
                returnState = false
            } else if (tujuanId == null) {
                showToast("Pastikan memilih nama siswa pada saran yang ada!")
                returnState = false
            }
        }

        if (layoutKelas.visibility == View.VISIBLE) {
            if (tujuanId == null) {
                showToast("Pilih kelas dulu!")
                returnState = false
            }
        }

        return returnState
    }

    private fun checkMenu(menu: String) {
        when (menu) {
            AnnouncementListFragment.TO_ALL -> {
                layoutKelas.visibility = View.GONE
                layoutSiswa.visibility = View.GONE
                announcementType = AnnouncementListFragment.TO_ALL
            }
            AnnouncementListFragment.TO_SISWA -> {
                layoutKelas.visibility = View.GONE
                layoutSiswa.visibility = View.VISIBLE
                announcementType = AnnouncementListFragment.TO_SISWA
            }
            else -> {
                layoutKelas.visibility = View.VISIBLE
                layoutSiswa.visibility = View.GONE
                announcementType = AnnouncementListFragment.TO_KELAS
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}