package app.siakad.siakadtk.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import app.siakad.siakadtk.MainActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.ui.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    private lateinit var pbLoading: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        etName = findViewById(R.id.edt_signup_name)
        etEmail = findViewById(R.id.et_signup_email)
        etPassword = findViewById(R.id.et_signup_password)
        etRepeatPassword = findViewById(R.id.et_signup_repeat_password)
        btnSignup = findViewById(R.id.btn_signup_daftar)
        tvLogin = findViewById(R.id.btn_signup_masuk)
        pbLoading = findViewById(R.id.loading)
    }

    private fun setupView() {
        btnSignup.setOnClickListener {
            val intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}