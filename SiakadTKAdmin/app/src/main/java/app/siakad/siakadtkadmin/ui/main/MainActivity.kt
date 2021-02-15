package app.siakad.siakadtkadmin.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.siakad.siakadtkadmin.R

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
        botnavMain = findViewById(R.id.nav_view)
        navconMain = findNavController(R.id.nav_host_fragment)
    }

    private fun setupView() {
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_product,
            R.id.navigation_dashboard,
            R.id.navigation_setting
        ))
        setupActionBarWithNavController(navconMain, appBarConfiguration)
        botnavMain.setupWithNavController(navconMain)
    }
}