package app.siakad.siakadtk.presentation.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.widget.*
import app.siakad.siakadtk.presentation.screens.main.MainActivity

import app.siakad.siakadtk.R
import app.siakad.siakadtk.presentation.screens.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView
    private lateinit var pbLoading: ProgressBar

    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        setupItemWiew()
        setupView()
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            navigateToMain()
        }
    }

    private fun setupItemWiew() {
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.et_login_password)
        btnLogin = findViewById(R.id.btn_login_masuk)
        tvForgotPassword = findViewById(R.id.tv_login_forgot_password)
        tvSignUp = findViewById(R.id.tv_login_daftar)
        pbLoading = findViewById(R.id.loading)
    }

    private fun setupView() {
        btnLogin.setOnClickListener {
            if (validateForm()) {
                login()
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast(getString(R.string.scs_login))
                    val user = auth.currentUser
                    navigateToMain()
                } else {
                    showToast(getString(R.string.fail_login))
                }

                if (!task.isSuccessful) {
                    showToast(getString(R.string.fail_login))
                }
            }

    }
    private fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
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

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}