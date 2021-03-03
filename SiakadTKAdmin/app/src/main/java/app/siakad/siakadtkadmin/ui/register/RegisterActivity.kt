package app.siakad.siakadtkadmin.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPasswd: EditText
    private lateinit var etConfirmPasswd: EditText
    private lateinit var btnSignup: CardView
    private lateinit var tvLogin: TextView
    private lateinit var pbLoading: ProgressBar

    private val fbAuth = FirebaseAuth.getInstance()

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
            if (validateInput()) {
                registerAdmin()
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

    private fun registerAdmin() {
        fbAuth.createUserWithEmailAndPassword(etEmail.text.toString(), etPasswd.text.toString()).addOnCompleteListener(
            OnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    showToast(getString(R.string.scs_regis))
                    navigateToMain()
                } else {
                    showToast(getString(R.string.fail_regis))
                }
            })
    }

    private fun navigateToMain() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}