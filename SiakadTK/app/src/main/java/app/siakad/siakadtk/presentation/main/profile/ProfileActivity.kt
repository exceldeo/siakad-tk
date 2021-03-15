package app.siakad.siakadtk.presentation.main.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.repositories.MainRepository
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    private val pageTitle = "Profil Siswa"

    private lateinit var toolbar: Toolbar
    private lateinit var civProfileImg: CircleImageView
    private lateinit var btnChangeProfileImg: TextView
    private lateinit var etName: EditText
    private lateinit var etClass: EditText
    private lateinit var etTahunAjaran: EditText
    private lateinit var etGender: EditText
    private lateinit var etBornDate: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etParentName: EditText
    private lateinit var etSchoolBefore: EditText
    private lateinit var btnCancel: CardView
    private lateinit var btnSimpan: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupItemView()
        setupView()
    }

    private fun setupView() {
        setupAppBar()

        btnSimpan.setOnClickListener {

        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        civProfileImg = findViewById(R.id.civ_aprofile_user_img)
        btnChangeProfileImg = findViewById(R.id.btn_aprofile_ubah_foto)
        etName = findViewById(R.id.et_aprofile_nama)
        etClass = findViewById(R.id.et_aprofile_kelas)
        etTahunAjaran = findViewById(R.id.et_aprofile_tahunajaran)
        etGender = findViewById(R.id.et_aprofile_jeniskelamin)
        etBornDate = findViewById(R.id.et_aprofile_ttl)
        etAddress = findViewById(R.id.et_aprofile_alamat)
        etPhoneNumber = findViewById(R.id.et_aprofile_no_hp_ortu)
        etParentName = findViewById(R.id.et_aprofile_nama_ortu)
        etSchoolBefore = findViewById(R.id.et_aprofile_asal_sekolah)
        btnCancel = findViewById(R.id.btn_aprofile_batal)
        btnSimpan = findViewById(R.id.btn_aprofile_simpan)

        etName.setText(MainRepository.currentUser.nama)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}