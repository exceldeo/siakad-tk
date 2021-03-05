package app.siakad.siakadtk.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceFragmentCompat
import app.siakad.siakadtk.R
import app.siakad.siakadtk.SplashActivity
import app.siakad.siakadtk.data.repositories.MainRepository
import app.siakad.siakadtk.presentation.history.HistoryActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private val pageTitle = "Pengaturan"

    private lateinit var toolbar: Toolbar
    private lateinit var btnLogout: Button
    private lateinit var btnUbahProfil: Button
    private lateinit var btnUbahPassword: Button
    private lateinit var btnUbahEmail: Button
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.settings, SettingsFragment())
//            .commit()
        setupItemView()
        setupView()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        btnLogout = findViewById(R.id.btn_settings_logout)
        btnUbahProfil = findViewById(R.id.btn_settings_lihatubahprofil)
        btnUbahEmail = findViewById(R.id.btn_settings_ubahemail)
        btnUbahPassword = findViewById(R.id.btn_settings_ubahpassword)
    }

    private fun setupView() {
        setupAppBar()

        btnLogout.setOnClickListener {
            logout()
            val intent = Intent(this@SettingsActivity, SplashActivity::class.java)
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

        }
    }

    private fun logout() {
        auth.signOut()
        showToast("Berhasiil logout")
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