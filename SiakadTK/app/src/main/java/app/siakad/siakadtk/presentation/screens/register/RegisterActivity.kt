package app.siakad.siakadtk.presentation.screens.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.infrastructure.viewmodels.screens.register.RegisterViewModel
import app.siakad.siakadtk.presentation.screens.login.LoginActivity
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener
import app.siakad.siakadtk.presentation.views.alert.AlertListener
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import java.io.File


class RegisterActivity : AppCompatActivity(), AlertListener, AuthenticationListener {
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var btnUploadBukti: Button

    private lateinit var vmRegister: RegisterViewModel

    private var firstPaymentImage: Uri? = null

    companion object {
        const val PICK_PHOTO_REQUEST = 1000
        const val PERMISSION_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()
        setupItemView()
        setupView()
    }

    override fun onStart() {
        super.onStart()
        if (AuthenticationRepository.fbAuth.currentUser != null) {
            navigateToMain()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
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

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_PHOTO_REQUEST) {
            val imageUri: Uri = data?.data!!
            btnUploadBukti.text = File(imageUri.path).name
            firstPaymentImage = imageUri
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            PICK_PHOTO_REQUEST
        )
    }

    private fun setupItemView() {
        etName = findViewById(R.id.edt_signup_name)
        etEmail = findViewById(R.id.et_signup_email)
        etPassword = findViewById(R.id.et_signup_password)
        etRepeatPassword = findViewById(R.id.et_signup_repeat_password)
        btnUploadBukti = findViewById(R.id.btn_signup_upload_bukti)
        btnSignup = findViewById(R.id.btn_signup_daftar)
        tvLogin = findViewById(R.id.btn_signup_masuk)
        pbLoading = findViewById(R.id.loading)

        vmRegister =
            ViewModelProvider(this, ViewModelFactory(this, this)).get(RegisterViewModel::class.java)
    }

    private fun setupView() {
        btnUploadBukti.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(
                        permissions,
                        PERMISSION_REQUEST
                    );
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }
        btnSignup.setOnClickListener {
            if (validateForm()) {
                registerUser()
                showToast("Sudah pendaftaran")
//                navigateToMain()
            } else {
                showToast("Ulangi pendaftaran")
            }
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        etPassword.transformationMethod = PasswordTransformationMethod()
        etRepeatPassword.transformationMethod = PasswordTransformationMethod()
    }

    private fun registerUser(){
        vmRegister.registerSiswa(
            etEmail.text.toString(),
            etPassword.text.toString()
        )
        vmRegister.insertPengguna(
            etEmail.text.toString(),
            etPassword.text.toString(),
            etName.text.toString(),
            firstPaymentImage
        )
    }
    private fun validateForm(): Boolean {
        var valid = true

        val email = etEmail.text.toString()
        if(TextUtils.isEmpty(email)) {
            etEmail.error = getString(R.string.empty_input)
            valid = false
        }
        val fullname = etName.text.toString()
        if(TextUtils.isEmpty(fullname)) {
            etName.error = getString(R.string.empty_input)
            valid = false
        }

        val password = etPassword.text.toString()
        if(TextUtils.isEmpty(password)) {
            etPassword.error = getString(R.string.empty_input)
            valid = false
        } else if (password.length < 6) {
            etPassword.error = getString(R.string.weak_passwd)
            valid = false
        }

        val repeatpassword = etRepeatPassword.text.toString()
        if(TextUtils.isEmpty(repeatpassword)) {
            etRepeatPassword.error = getString(R.string.empty_input)
            valid = false
        } else if(repeatpassword != password) {
            etRepeatPassword.error = getString(R.string.err_conf_passwd)
            valid = false
        }

        if (firstPaymentImage == null) {
            val alertDialog = AlertDialogFragment(
                "Foto belum ditambahkan!",
                "Apakah Anda yakin menyimpan data tanpa menggunakan foto?"
            )
            alertDialog.show(supportFragmentManager, null)
            valid = false
        }

        return valid
    }

    override fun navigateToMain() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun alertAction() {
        registerUser()
    }
}