package app.siakad.siakadtkadmin.infrastructure.viewmodels.login

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.domain.repositories.LoginRepository
import app.siakad.siakadtkadmin.infrastructure.DataContainer
import app.siakad.siakadtkadmin.infrastructure.ModelState
import app.siakad.siakadtkadmin.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val loginRepository = LoginRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var loginObserver: Observer<DataContainer<String>>

    init {
        setupObserver()
    }

    fun loginAdmin(email: String, passwd: String) {
        vmCoroutineScope.launch {
            loginRepository.login(email, passwd)
        }
    }

    private fun setupObserver() {
        loginObserver = Observer {
            val data = it

            if (data.status == ModelState.SUCCESS) {
                (context as AuthenticationListener).showToast(data.data.toString())
                (context as AuthenticationListener).navigateToMain()
            } else {
                (context as AuthenticationListener).showToast(data.message.toString())
            }
        }

        loginRepository.getloginState().observe(lcOwner, loginObserver)
    }
}