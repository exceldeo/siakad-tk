package app.siakad.siakadtk.presentation.screens.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.infrastructure.viewmodels.screens.registration.RegistrationFormViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.registration.form.RegistrationFormActivity

class RegistrationActivity : AppCompatActivity() {
    private val pageTitle = "Daftar Ulang"

    private lateinit var toolbar: Toolbar
    private lateinit var tvRegistrationStatus: TextView
    private lateinit var ivRegistrationStatus: ImageView
    private lateinit var btnRegistrationForm: Button

    private lateinit var tvName: TextView
    private lateinit var tvBornDate: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvClass: TextView
    private lateinit var tvParentName: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvClassYear: TextView

    private lateinit var vmRegistrationForm: RegistrationFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setupItemView()
        setupAppBar()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvRegistrationStatus = findViewById(R.id.tv_registration_desc_status)
        ivRegistrationStatus = findViewById(R.id.iv_registration_status)
        btnRegistrationForm = findViewById(R.id.btn_registration_go_to_form)

        tvName = findViewById(R.id.tv_registration_dp_nama)
        tvBornDate = findViewById(R.id.tv_registration_dp_ttl)
        tvGender = findViewById(R.id.tv_registration_dp_gender)
        tvClass = findViewById(R.id.tv_registration_dp_class)
        tvParentName = findViewById(R.id.tv_registration_dp_nama_ortu)
        tvAddress = findViewById(R.id.tv_registration_dp_addres)
        tvPhone = findViewById(R.id.tv_registration_dp_no_hp)
        tvClassYear = findViewById(R.id.tv_registration_dp_thn_ajaran)
    }

    private fun setupView() {
        setupAppBar()
        btnRegistrationForm.setOnClickListener{
            val intent = Intent(this@RegistrationActivity, RegistrationFormActivity::class.java)
            startActivity(intent)
        }

        vmRegistrationForm = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(RegistrationFormViewModel::class.java)

        val obsRegistrationGetUser = Observer<Pengguna> {
            tvName.text = it.nama
            tvBornDate.text = it.detail!!.tanggalLahir
            tvGender.text = it.detail!!.jenisKelamin
            tvClass.text = it.detail!!.kelas
            tvParentName.text = it.detail!!.namaOrtu
            tvAddress.text = it.alamat
            tvPhone.text = it.noHP
            tvClassYear.text = it.detail!!.tahunAjaran
        }
        vmRegistrationForm.getUserData()
            .observe(this, obsRegistrationGetUser)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}