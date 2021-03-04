package app.siakad.siakadtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class PasswordActivity : AppCompatActivity() {
    private val pageTitle = "Ubah Password"

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
    }

    private fun setupView() {
        setupAppBar()
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}