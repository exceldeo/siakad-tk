package app.siakad.siakadtkadmin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.login.LoginActivity
import app.siakad.siakadtkadmin.ui.main.MainActivity
import app.siakad.siakadtkadmin.ui.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        mainScope.launch {
            delay(2000)

            val intent = Intent(this@SplashScreenActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}