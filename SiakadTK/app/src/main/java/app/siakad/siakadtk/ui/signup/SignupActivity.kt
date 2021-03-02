package app.siakad.siakadtk.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import app.siakad.siakadtk.MainActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var btnUploadBukti: Button

    private val REQUEST_CAMERA = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        setupItemView()
        setupView()
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUI(auth.currentUser)
                Toast.makeText(this@SignupActivity,
                    "Reload successful!",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SignupActivity,
                    "Failed to reload user.",
                    Toast.LENGTH_SHORT).show()
            }
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
    }

    private fun setupView() {
        btnUploadBukti.setOnClickListener {
        }
        btnSignup.setOnClickListener {
            createAccount(etEmail.text.toString(), etName.text.toString(), etPassword.text.toString(), etRepeatPassword.text.toString())
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
    
    private fun createAccount(email: String, fullname: String, password: String, repeatpassword: String) {
        if(!validateForm()){
            return
        }
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
                
            }
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    checkForMultiFactorFailure(task.exception!!)
                }

                if (!task.isSuccessful) {
                    //
                }
            }

    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Toast.makeText(baseContext,
                        "Verification email sent to ${user.email} ",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkForMultiFactorFailure(exception: Exception) {

    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = etEmail.text.toString()
        if(TextUtils.isEmpty(email)) {
            etEmail.error = "Required."
            valid = false
        } else {
            etEmail.error = null
        }

        val fullname = etName.text.toString()
        if(TextUtils.isEmpty(fullname)) {
            etPassword.error = "Required."
            valid = false
        } else {
            etPassword.error = null
        }

        val password = etPassword.text.toString()
        if(TextUtils.isEmpty(password)) {
            etPassword.error = "Required."
            valid = false
        } else {
            etPassword.error = null
        }

        val repeatpassword = etRepeatPassword.text.toString()
        if(TextUtils.isEmpty(repeatpassword)) {
            etRepeatPassword.error = "Required."
            valid = false
        } else {
            etRepeatPassword.error = null
        }


        return valid
    }

}