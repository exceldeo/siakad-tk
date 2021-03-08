package app.siakad.siakadtk.presentation.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtk.R
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.androidbuffer.kotlinfilepicker.KotRequest

class RegistrationFormActivity : AppCompatActivity() {
    private val pageTitle = "Form Pendaftaran"

    private lateinit var toolbar: Toolbar
    private lateinit var etName: EditText
    private lateinit var etBornDate: EditText
    private lateinit var spGender: Spinner
    private lateinit var spClass: Spinner
    private lateinit var etParentName: EditText
    private lateinit var etAddress: EditText
    private lateinit var spTahunAjaran: Spinner
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnUploadBukti: Button
    private lateinit var etSchoolBefore: EditText
    private lateinit var btnCancel: TextView
    private lateinit var btnSimpan: TextView

    private val REQUEST_FILE = 1001
    private val REQUEST_CAMERA = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_form)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        etName = findViewById(R.id.et_registrationform_nama)
        etBornDate = findViewById(R.id.et_registrationform_ttl)
        spGender = findViewById(R.id.sp_registrationform_jenis_kelamin)
        spClass = findViewById(R.id.sp_registrationform_kelas)
        etParentName = findViewById(R.id.et_registrationform_nama_ortu)
        etAddress = findViewById(R.id.et_registrationform_alamat)
        spTahunAjaran = findViewById(R.id.sp_registrationform_tahun_ajaran)
        etPhoneNumber = findViewById(R.id.et_registrationform_no_hp_ortu)
        btnUploadBukti = findViewById(R.id.btn_registrationform_upload_bukti_bayar)
        etSchoolBefore = findViewById(R.id.et_registrationform_asal_sekolah)
        btnCancel = findViewById(R.id.btn_registrationform_batal)
        btnSimpan = findViewById(R.id.btn_registrationform_simpan)
    }

    private fun setupView() {
        setupAppBar()
        btnUploadBukti.setOnClickListener {
            showFileChooser()
        }
        btnCancel.setOnClickListener{

        }
        btnSimpan.setOnClickListener {

        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showFileChooser() {
        KotRequest.File(this, REQUEST_FILE)
            .setMimeType(KotConstants.FILE_TYPE_FILE_ALL)
            .pick()
        KotRequest.Camera(this).setRequestCode(REQUEST_CAMERA).pick()
    }

}