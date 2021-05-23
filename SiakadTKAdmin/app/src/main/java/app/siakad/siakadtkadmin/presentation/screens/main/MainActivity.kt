package app.siakad.siakadtkadmin.presentation.screens.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.ClassroomAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.main.MainViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.main.setting.SettingFragment
import app.siakad.siakadtkadmin.presentation.screens.splash.SplashScreenActivity
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener

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
    botnavMain = findViewById(R.id.nav_view)
    navconMain = findNavController(R.id.nav_host_fragment)
    vmMain = ViewModelProvider(
      this,
      ViewModelFactory(
        this,
        this
      )
    ).get(MainViewModel::class.java)
  }

  private fun setupView() {
    botnavMain.setupWithNavController(navconMain)
  }

  override fun alertAction(tag: String?) {
    if (tag == SettingFragment.TAG_LOGOUT) {
      vmMain.logout()
      val intent = Intent(this@MainActivity, SplashScreenActivity::class.java)
      startActivity(intent)
      finish()
    } else if (tag == SettingFragment.TAG_RESET) {
      vmMain.resetDaftarUlang()
    }
  }
}