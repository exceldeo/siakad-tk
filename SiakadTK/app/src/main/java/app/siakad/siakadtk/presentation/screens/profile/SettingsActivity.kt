package app.siakad.siakadtk.presentation.screens.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceFragmentCompat
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.presentation.screens.splash.SplashActivity
import app.siakad.siakadtk.presentation.views.alert.AlertDialogFragment
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private val pageTitle = "Pengaturan"

    private lateinit var toolbar: Toolbar
    private lateinit var btnLogout: Button
    private lateinit var btnUbahProfil: TextView
    private lateinit var btnUbahPassword: TextView
    private lateinit var btnUbahEmail: TextView

    private val authRepository = AuthenticationRepository()
    private lateinit var alertDialog: AlertDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        btnLogout = findViewById(R.id.btn_settings_logout)
        btnUbahProfil = findViewById(R.id.btn_settings_lihatubahprofil)
        btnUbahEmail = findViewById(R.id.btn_settings_ubahemail)
        btnUbahPassword = findViewById(R.id.btn_settings_ubahpassword)

        alertDialog = AlertDialogFragment("Keluar", "Apakah Anda yakin ingin keluar?")
    }

    private fun setupView() {
        setupAppBar()

        btnLogout.setOnClickListener {
            logout()
            val intent = Intent(this@SettingsActivity, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnUbahProfil.setOnClickListener {
            val intent = Intent(this@SettingsActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnUbahPassword.setOnClickListener {
            val intent = Intent(this@SettingsActivity, PasswordActivity::class.java)
            startActivity(intent)
        }

        btnUbahEmail.setOnClickListener {
            val intent = Intent(this@SettingsActivity, EmailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logout() {
        authRepository.logout()
        showToast("Berhasil logout")
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}