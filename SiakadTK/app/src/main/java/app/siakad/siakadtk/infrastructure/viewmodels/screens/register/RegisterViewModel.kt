package app.siakad.siakadtk.infrastructure.viewmodels.screens.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel() {
    private val authRepository = AuthenticationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var email: String = ""
    private var passwd: String = ""
    private var name: String = ""

    private lateinit var regObserver: Observer<ModelContainer<String>>
    private lateinit var userObserver: Observer<ModelContainer<String>>

    init {
        setupObserver()
    }

    fun registerSiswa(email: String, passwd: String, name: String) {
        this.email = email
        this.passwd = passwd
        this.name = name

        vmCoroutineScope.launch {
            authRepository.register(email, passwd)
        }
    }

    private fun setupObserver() {
        regObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                userRepository.insertData(Pengguna(
                    email = email,
                    passwd = passwd,
                    nama = name
                ))
            } else {
                showToast(context.getString(R.string.fail_regis))
            }
        }

        userObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                showToast(context.getString(R.string.scs_regis))
                (context as AuthenticationListener).navigateToMain()
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_set_user))
            }
        }

        authRepository.getAuthState().observe(lcOwner, regObserver)
        userRepository.getInsertState().observe(lcOwner, userObserver)
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}