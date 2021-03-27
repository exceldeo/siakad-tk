package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val regRepository = AuthenticationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var email: String = ""
    private var passwd: String = ""
    
    private lateinit var regObserver: Observer<ModelContainer<String>>
    private lateinit var userObserver: Observer<ModelContainer<String>>

    init {
        setupObserver()
    }

    fun registerAdmin(email: String, passwd: String) {
        this.email = email
        this.passwd = passwd
        
        vmCoroutineScope.launch {
            regRepository.register(email, passwd)
        }
    }

    private fun setupObserver() {
        regObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                userRepository.insertData(PenggunaModel(
                    email = email,
                    passwd = passwd,
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

        regRepository.getAuthState().observe(lcOwner, regObserver)
        userRepository.getInsertState().observe(lcOwner, userObserver)
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}