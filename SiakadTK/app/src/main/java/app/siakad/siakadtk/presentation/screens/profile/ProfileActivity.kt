package app.siakad.siakadtk.presentation.screens.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile.ProfileViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.views.date.DateListener
import app.siakad.siakadtk.presentation.views.date.DatePickerFragment
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import app.siakad.siakadtk.domain.models.KelasModel
import app.siakad.siakadtk.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtk.presentation.views.alert.AlertListener
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, DateListener, AlertListener {
    private val pageTitle = "Profil Siswa"

    private lateinit var toolbar: Toolbar
    private lateinit var civProfileImg: CircleImageView
    private lateinit var btnChangeProfileImg: TextView
    private lateinit var etName: EditText
    private lateinit var tvClass: TextView
    private lateinit var tvTahunAjaran: TextView
    private lateinit var tvGender: TextView
    private lateinit var etBornDate: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etParentName: EditText
    private lateinit var btnCancel: TextView
    private lateinit var btnSimpan: TextView

    private lateinit var datePicker: DatePickerFragment
    private lateinit var calendar: Calendar

    private lateinit var vmProfile : ProfileViewModel

    private var dataUser = Pengguna()
    private var dataKelasUser = KelasModel()
    private var profileImage: Uri? = null

    companion object {
        const val PICK_PHOTO_REQUEST = 1000
        const val PERMISSION_REQUEST = 1001
    }

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
            val alertDialog = AlertDialogFragment(
                "Konfirmasi penggantian profil",
                "Apakah Anda yakin mengubah data profil?"
            )
            alertDialog.show(supportFragmentManager, null)
        }
        btnChangeProfileImg.setOnClickListener {
            pickImage()
        }
        vmProfile = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(ProfileViewModel::class.java)

        val obsProfileGetUser = Observer<Pengguna> {
            dataUser = it
            etName.setText(it.nama)
            tvGender.text = it.detail!!.jenisKelamin
            etBornDate.setText(it.detail!!.tanggalLahir)
            etAddress.setText(it.alamat)
            etPhoneNumber.setText(it.noHP)
            etParentName.setText(it.detail!!.namaOrtu)
            if(it.detail!!.fotoSiswa != "") Picasso.with(this.applicationContext).load(it.detail!!.fotoSiswa).into(civProfileImg)

            vmProfile.setKelasName(it.detail!!.kelasId)
        }

        vmProfile.getUserData()
            .observe(this, obsProfileGetUser)

        val obsRegistrationGetClass = Observer<KelasModel> {
            dataKelasUser = it

            tvTahunAjaran.text = it.tahunMulai.toString() + "/" + it.tahunSelesai.toString()
            tvClass.text = it.namaKelas
        }

        vmProfile.getClassroomListById()
            .observe(this, obsRegistrationGetClass)
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        civProfileImg = findViewById(R.id.civ_aprofile_user_img)
        btnChangeProfileImg = findViewById(R.id.btn_aprofile_ubah_foto)
        etName = findViewById(R.id.et_aprofile_nama)
        tvClass = findViewById(R.id.tv_aprofile_kelas)
        tvTahunAjaran = findViewById(R.id.tv_aprofile_tahunajaran)
        tvGender = findViewById(R.id.tv_aprofile_jeniskelamin)
        etBornDate = findViewById(R.id.et_aprofile_ttl)
        etAddress = findViewById(R.id.et_aprofile_alamat)
        etPhoneNumber = findViewById(R.id.et_aprofile_no_hp_ortu)
        etParentName = findViewById(R.id.et_aprofile_nama_ortu)
        btnCancel = findViewById(R.id.btn_aprofile_batal)
        btnSimpan = findViewById(R.id.btn_aprofile_simpan)

        datePicker = DatePickerFragment()
        calendar = Calendar.getInstance()
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

    private fun pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                requestPermissions(
                    permissions,
                    ProfileActivity.PERMISSION_REQUEST
                );
            } else {
                pickImageFromGallery();
            }
        } else {
            pickImageFromGallery();
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            ProfileActivity.PICK_PHOTO_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            ProfileActivity.PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Akses ditolak", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == ProfileActivity.PICK_PHOTO_REQUEST) {
            val imageUri: Uri = data?.data!!
            profileImage = imageUri
            Picasso.with(this.applicationContext).load(imageUri).into(civProfileImg)
        }
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@ProfileActivity,
                SettingsActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
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

    override fun alertAction(tag: String?) {
        if (validateInput()) {
            vmProfile.setData(
                etName.text.toString(),
                tvClass.text.toString(),
                etParentName.text.toString(),
                tvTahunAjaran.text.toString(),
                tvGender.text.toString(),
                etBornDate.text.toString(),
                etAddress.text.toString(),
                etPhoneNumber.text.toString(),
                profileImage
            )
            navigateBack()
        }
    }
}