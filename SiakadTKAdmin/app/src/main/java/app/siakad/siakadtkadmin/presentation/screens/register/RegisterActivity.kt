package app.siakad.siakadtkadmin.presentation.screens.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.register.RegisterViewModel
import app.siakad.siakadtkadmin.presentation.screens.login.LoginActivity
import app.siakad.siakadtkadmin.presentation.screens.main.MainActivity
import app.siakad.siakadtkadmin.presentation.utils.listener.AuthenticationListener

class RegisterActivity : AppCompatActivity(), AuthenticationListener {

    private lateinit var etEmail: EditText
    private lateinit var etPasswd: EditText
    private lateinit var etConfirmPasswd: EditText
    private lateinit var btnSignup: CardView
    private lateinit var tvLogin: TextView
    private lateinit var pbLoading: ProgressBar

    private lateinit var vmRegister: RegisterViewModel

    override fun onStart() {
        super.onStart()
        if (AuthenticationRepository.fbAuth.currentUser != null) {
            navigateToMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        etEmail = findViewById(R.id.et_register_email)
        etPasswd = findViewById(R.id.et_register_password)
        etConfirmPasswd = findViewById(R.id.et_register_password_konfirmasi)
        btnSignup = findViewById(R.id.btn_register_daftar)
        tvLogin = findViewById(R.id.tv_register_masuk)
        pbLoading = findViewById(R.id.loading)

        vmRegister =
            ViewModelProvider(this, ViewModelFactory(this, this)).get(RegisterViewModel::class.java)
    }

    private fun setupView() {
        btnSignup.setOnClickListener {
            if (validateInput()) {
                vmRegister.registerAdmin(etEmail.text.toString(), etPasswd.text.toString())
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        etPasswd.transformationMethod = PasswordTransformationMethod()
        etConfirmPasswd.transformationMethod = PasswordTransformationMethod()
    }

    private fun validateInput(): Boolean {
        var returnState = true

        if (etEmail.text.isEmpty()) {
            etEmail.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etPasswd.text.isEmpty()) {
            etPasswd.error = getString(R.string.empty_input)
            returnState = false
        } else if (etPasswd.text.length < 6) {
            etPasswd.error = getString(R.string.weak_passwd)
            returnState = false
        }

        if (etConfirmPasswd.text.toString() != etPasswd.text.toString()) {
            etConfirmPasswd.error = getString(R.string.err_conf_passwd)
            returnState = false
        }

        return returnState
    }

    override fun navigateToMain() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)

        startActivity(intent)
        finish()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}