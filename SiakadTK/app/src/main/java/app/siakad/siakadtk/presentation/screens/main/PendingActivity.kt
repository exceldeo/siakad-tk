package app.siakad.siakadtk.presentation.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.utils.listeners.login.LoginListener
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.infrastructure.viewmodels.screens.login.LoginViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.splash.SplashActivity
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener

class PendingActivity : AppCompatActivity(), AuthenticationListener {
    private lateinit var btnLogout: Button
    private val authRepository = AuthenticationRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending)
        supportActionBar?.hide()

        setupItemView()
        setupView()
    }

    override fun onStart() {
        super.onStart()
        if (AuthenticationRepository.fbAuth.currentUser != null) {
            if (AuthenticationRepository.userState) {
                navigateToMain()
            }
        }
    }

    private fun setupView() {
        btnLogout.setOnClickListener{
            logout()
        }
    }

    private fun setupItemView() {
        btnLogout = findViewById(R.id.btn_pending_logout)
    }

    private fun logout() {
        authRepository.logout()
        val intent = Intent(this@PendingActivity, SplashActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToMain() {
        val intent = Intent(this@PendingActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToPendingMain() {
        val intent = Intent(this@PendingActivity, PendingActivity::class.java)
        startActivity(intent)
        finish()
    }

     override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}