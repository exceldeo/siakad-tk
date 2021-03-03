package app.siakad.siakadtkadmin.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.cardview.widget.CardView

import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.main.MainActivity
import app.siakad.siakadtkadmin.ui.register.RegisterActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPasswd: EditText
    private lateinit var btnLogin: CardView
    private lateinit var tvSignUp: TextView
    private lateinit var pbLoading: ProgressBar

    private val fbAuth = FirebaseAuth.getInstance()

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
        btnLogin.setOnClickListener {
            if (validateInput()) {
                loginAdmin()
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
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

    private fun loginAdmin() {
        fbAuth.signInWithEmailAndPassword(etEmail.text.toString(), etPasswd.text.toString()).addOnSuccessListener(
            object : OnSuccessListener<AuthResult> {
                override fun onSuccess(p0: AuthResult?) {
                    showToast(getString(R.string.scs_login))
                    navigateToMain()
                }
            })
            .addOnFailureListener(this, OnFailureListener {
                e: Exception -> showToast(getString(R.string.fail_login))
            })
    }

    private fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}