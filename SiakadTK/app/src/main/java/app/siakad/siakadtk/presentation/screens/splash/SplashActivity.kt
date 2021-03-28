package app.siakad.siakadtk.presentation.screens.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.siakad.siakadtk.R
import app.siakad.siakadtk.presentation.screens.signup.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        mainScope.launch {
            delay(2000)

            val intent = Intent(this@SplashActivity, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}