package app.siakad.siakadtkadmin.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.login.LoggedInUserView
import app.siakad.siakadtkadmin.ui.login.LoginActivity
import app.siakad.siakadtkadmin.ui.login.LoginViewModel
import app.siakad.siakadtkadmin.ui.login.LoginViewModelFactory
import app.siakad.siakadtkadmin.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPasswd: EditText
    private lateinit var etConfirmPasswd: EditText
    private lateinit var btnSignup: CardView
    private lateinit var tvLogin: TextView
    private lateinit var pbLoading: ProgressBar

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
    }

    private fun setupView() {
        btnSignup.setOnClickListener {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName

        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}