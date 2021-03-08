package app.siakad.siakadtk.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtk.R

class RegistrationActivity : AppCompatActivity() {
    private val pageTitle = "Daftar Ulang"

    private lateinit var toolbar: Toolbar
    private lateinit var tvRegistrationStatus: TextView
    private lateinit var ivRegistrationStatus: ImageView
    private lateinit var btnRegistrationForm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvRegistrationStatus = findViewById(R.id.tv_registration_desc_status)
        ivRegistrationStatus = findViewById(R.id.iv_registration_status)
        btnRegistrationForm = findViewById(R.id.btn_registration_go_to_form)
    }

    private fun setupView() {
        setupAppBar()
        btnRegistrationForm.setOnClickListener{
            val intent = Intent(this@RegistrationActivity, RegistrationFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}