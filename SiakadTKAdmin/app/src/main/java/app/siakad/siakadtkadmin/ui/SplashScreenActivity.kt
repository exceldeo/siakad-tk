package app.siakad.siakadtkadmin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.siakad.siakadtkadmin.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
    }
}