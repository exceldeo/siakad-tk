package app.siakad.siakadtk.presentation.screens.registration

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.KelasModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.infrastructure.viewmodels.screens.registration.RegistrationFormViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.registration.form.RegistrationFormActivity
import app.siakad.siakadtk.presentation.views.preview.ImagePreviewActivity
import kotlinx.android.synthetic.main.activity_registration.*

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
    private lateinit var llViewData: LinearLayout
    private lateinit var btnLihatBukti: Button
    private lateinit var cvToForm: CardView
    private var dataUser = Pengguna()
    private var dataDaful = DaftarUlang()
    private var dataKelasUser = KelasModel()
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
        llViewData = findViewById(R.id.ll_data_pendaftaran)

        tvName = findViewById(R.id.tv_registration_dp_nama)
        tvBornDate = findViewById(R.id.tv_registration_dp_ttl)
        tvGender = findViewById(R.id.tv_registration_dp_gender)
        tvClass = findViewById(R.id.tv_registration_dp_class)
        tvParentName = findViewById(R.id.tv_registration_dp_nama_ortu)
        tvAddress = findViewById(R.id.tv_registration_dp_addres)
        tvPhone = findViewById(R.id.tv_registration_dp_no_hp)
        tvClassYear = findViewById(R.id.tv_registration_dp_thn_ajaran)
        btnLihatBukti = findViewById(R.id.btn_registration_lihat_bukti)

        cvToForm = findViewById(R.id.cv_registration_to_form)
    }

    @SuppressLint("SetTextI18n")
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

        val obsRegistrationGetUser = Observer<Pengguna> { it ->
            dataUser = it
            tvName.text = it.nama
            tvBornDate.text = it.detail!!.tanggalLahir
            tvGender.text = it.detail!!.jenisKelamin
            tvParentName.text = it.detail!!.namaOrtu
            tvAddress.text = it.alamat
            tvPhone.text = it.noHP

            vmRegistrationForm.setKelasName(it.detail!!.kelasId)
            dataKelasUser.kelasId = it.detail!!.kelasId

            if (it.detail!!.kelasId == "") {
                llViewData.visibility = View.INVISIBLE
                tvRegistrationStatus.text = this.getString(R.string.status_daful_0)
                ivRegistrationStatus.setImageResource(R.drawable.ic_belum_isi)
            }
            else
            {
                if(it.detail!!.dafulState)
                {
                    tvRegistrationStatus.text = this.getString(R.string.status_daful_2)
                    ivRegistrationStatus.setImageResource(R.drawable.ic_status_selesai)
                } else {
                    if(dataDaful.dafulId == "" && dataDaful.fotoBayar == "") {
                        tvRegistrationStatus.text = this.getString(R.string.status_daful_3)
                        ivRegistrationStatus.setImageResource(R.drawable.ic_status_revisi)
                    } else {
                        tvRegistrationStatus.text = this.getString(R.string.status_daful_1)
                        ivRegistrationStatus.setImageResource(R.drawable.ic_status_proses)
                    }
                }
                llViewData.visibility = View.VISIBLE
            }
        }
        vmRegistrationForm.getUserData()
            .observe(this, obsRegistrationGetUser)

        val obsRegistrationGetClass = Observer<KelasModel> {
            dataKelasUser = it

            tvClassYear.text = it.tahunMulai.toString() + "/" + it.tahunSelesai.toString()
            tvClass.text = it.namaKelas 
        }

        vmRegistrationForm.getClassroomListById()
            .observe(this, obsRegistrationGetClass)

        val obsRegistrationGetDaful = Observer<DaftarUlang> {
            dataDaful = it
        }
        vmRegistrationForm.getDaftarUlangData()
            .observe(this, obsRegistrationGetDaful)

        btnLihatBukti.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, ImagePreviewActivity::class.java)
            intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE_TYPE, ImagePreviewActivity.IMG_URL)
            intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE, dataDaful.fotoBayar) //harusnya foto daful artintinya perlu get dafulnya
            startActivity(intent)
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}