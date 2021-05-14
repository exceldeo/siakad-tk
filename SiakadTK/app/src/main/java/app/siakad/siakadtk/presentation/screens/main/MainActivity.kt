package app.siakad.siakadtk.presentation.screens.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.MainViewModel
import app.siakad.siakadtk.presentation.screens.splash.SplashActivity
import app.siakad.siakadtk.presentation.views.alert.AlertListener

class MainActivity : AppCompatActivity(), AlertListener {

    private lateinit var botnavMain: BottomNavigationView
    private lateinit var navconMain: NavController

    private lateinit var vmMain: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        botnavMain = findViewById(R.id.botnav_view)
        navconMain = findNavController(R.id.nav_host_fragment)
        vmMain = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setupView() {
        botnavMain.setupWithNavController(navconMain)
    }

    override fun alertAction(tag: String?) {
        vmMain.logout()
        val intent = Intent(this@MainActivity, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
}