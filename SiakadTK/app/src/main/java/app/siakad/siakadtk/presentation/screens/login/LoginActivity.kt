package app.siakad.siakadtk.presentation.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.presentation.screens.main.MainActivity

import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.infrastructure.viewmodels.screens.login.LoginViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.register.RegisterViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.main.PendingActivity
import app.siakad.siakadtk.presentation.screens.register.RegisterActivity
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener

class LoginActivity : AppCompatActivity(), AuthenticationListener {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView
    private lateinit var pbLoading: ProgressBar

    private lateinit var vmLogin: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        setupItemWiew()
        setupView()
    }

    override fun onStart() {
        super.onStart()
        if (AuthenticationRepository.fbAuth.currentUser != null) {
//            if (AuthenticationRepository.userState) {
                navigateToMain()
//            } else {
//                navigateToPendingMain()
//            }
        }
    }

    private fun setupItemWiew() {
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.et_login_password)
        btnLogin = findViewById(R.id.btn_login_masuk)
        tvForgotPassword = findViewById(R.id.tv_login_forgot_password)
        tvSignUp = findViewById(R.id.tv_login_daftar)
        pbLoading = findViewById(R.id.loading)

        vmLogin =
            ViewModelProvider(this, ViewModelFactory(this, this)).get(LoginViewModel::class.java)
    }

    private fun setupView() {
        etPassword.transformationMethod = PasswordTransformationMethod()

        btnLogin.setOnClickListener {
            if (validateForm()) {
                vmLogin.loginSiswa(etEmail.text.toString(), etPassword.text.toString())
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToPendingMain() {
        val intent = Intent(this@LoginActivity, PendingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = etEmail.text.toString()
        if(TextUtils.isEmpty(email)) {
            etEmail.error = getString(R.string.empty_input)
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

        return valid
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}