package app.siakad.siakadtk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.siakad.siakadtk.ui.signup.SignupActivity
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

            val intent = Intent(this@SplashScreenActivity, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}