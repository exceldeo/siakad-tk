package app.siakad.siakadtkadmin.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.data.repositories.MainRepository
import app.siakad.siakadtkadmin.presentation.main.MainActivity
import app.siakad.siakadtkadmin.presentation.register.RegisterActivity
import app.siakad.siakadtkadmin.presentation.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.utils.listener.AuthenticationListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class LoginActivity : AppCompatActivity(), AuthenticationListener {

    private lateinit var etEmail: EditText
    private lateinit var etPasswd: EditText
    private lateinit var btnLogin: CardView
    private lateinit var tvSignUp: TextView
    private lateinit var pbLoading: ProgressBar

    private lateinit var vmLogin: LoginViewModel
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (fbAuth.currentUser != null) {
            navigateToMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        etEmail = findViewById(R.id.et_login_email)
        etPasswd = findViewById(R.id.et_login_password)
        btnLogin = findViewById(R.id.btn_login_masuk)
        tvSignUp = findViewById(R.id.tv_login_daftar)
        pbLoading = findViewById(R.id.loading)
    }

    private fun setupView() {
        etPasswd.transformationMethod = PasswordTransformationMethod()

        btnLogin.setOnClickListener {
            if (validateInput()) {
                vmLogin.loginAdmin(etEmail.text.toString(), etPasswd.text.toString())
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        vmLogin =
            ViewModelProvider(this, ViewModelFactory(this, this)).get(LoginViewModel::class.java)
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

        return returnState
    }

    override fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)

        startActivity(intent)
        finish()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}