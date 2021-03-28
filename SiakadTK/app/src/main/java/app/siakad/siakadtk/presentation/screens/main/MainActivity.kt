package app.siakad.siakadtk.presentation.screens.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import app.siakad.siakadtk.R

class MainActivity : AppCompatActivity() {

    private lateinit var botnavMain: BottomNavigationView
    private lateinit var navconMain: NavController

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
    }
    private fun setupView() {
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_product,
            R.id.navigation_profile
        ))
//        setupActionBarWithNavController(navconMain, appBarConfiguration)
        botnavMain.setupWithNavController(navconMain)
    }
}