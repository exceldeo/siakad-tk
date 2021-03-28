package app.siakad.siakadtk.presentation.screens.signup

import android.content.Intent
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


class SignupActivity : AppCompatActivity(), AuthenticationListener {
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var btnUploadBukti: Button

    private lateinit var vmRegister: RegisterViewModel

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
        }
        btnSignup.setOnClickListener {
            if (validateForm()) {
                vmRegister.registerSiswa(etEmail.text.toString(), etPassword.text.toString(), etName.text.toString())
            } else {
                showToast("Ulangi pendaftaran")
            }
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        etPassword.transformationMethod = PasswordTransformationMethod()
        etRepeatPassword.transformationMethod = PasswordTransformationMethod()
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

        return valid
    }

    override fun navigateToMain() {
        val intent = Intent(this@SignupActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}