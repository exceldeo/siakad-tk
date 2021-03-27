package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.ModelState
import app.siakad.siakadtkadmin.domain.models.UserModel
import app.siakad.siakadtkadmin.domain.models.UserRoleModel
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val loginRepository = AuthenticationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    
    private var email: String = ""
    private var passwd: String = ""
    private var userId: String = ""

    private lateinit var loginObserver: Observer<ModelContainer<String>>
    private lateinit var userObserver: Observer<ModelContainer<UserModel>>

    init {
        setupObserver()
    }

    fun loginAdmin(email: String, passwd: String) {
        this.email = email
        this.passwd = passwd
        
        vmCoroutineScope.launch {
            userRepository.getUserByEmail(email)
        }
    }

    private fun setupObserver() {
        userObserver = Observer { user -> 
            if (user.status == ModelState.SUCCESS) {
                val item = user.data

                if (item != null) {
                    if (item.role == UserRoleModel.ADMIN.str) {
                        loginRepository.login(email, passwd)
                        userId = item.userId.toString()
                    } else {
                        showToast(context.getString(R.string.fail_login_not_admin))
                    }
                }
            } else if (user.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_user))
            }
        }
        
        loginObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                showToast(context.getString(R.string.scs_login))

                if (!AuthenticationRepository.userState) {
                    AuthenticationRepository.setUser(userId, email, passwd)
                }

                (context as AuthenticationListener).navigateToMain()
            } else {
                showToast(context.getString(R.string.fail_login))
            }
        }

        userRepository.getUser().observe(lcOwner, userObserver)
        loginRepository.getAuthState().observe(lcOwner, loginObserver)
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}