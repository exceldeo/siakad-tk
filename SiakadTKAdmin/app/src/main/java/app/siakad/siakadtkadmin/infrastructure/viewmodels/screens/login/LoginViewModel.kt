package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.listeners.login.LoginListener
import app.siakad.siakadtkadmin.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), LoginListener {
    private val loginRepository = AuthenticationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    
    private var email: String = ""
    private var passwd: String = ""
    private var userId: String = ""

    override fun setUer(user: ModelContainer<PenggunaModel>) {
        if (user.status == ModelState.SUCCESS) {
            val item = user.data

            if (item != null) {
                if (item.role == UserRoleModel.ADMIN.str) {
                    loginRepository.login(this@LoginViewModel, email, passwd)
                    userId = item.userId
                } else {
                    showToast(context.getString(R.string.fail_login_not_admin))
                }
            }
        } else if (user.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    override fun notifyLoginStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_login))

            if (!AuthenticationRepository.userState) {
                AuthenticationRepository.setUser(userId, email, passwd)
            }
            (context as AuthenticationListener).navigateToMain()
        } else {
            showToast(context.getString(R.string.fail_login))
        }
    }

    fun loginAdmin(email: String, passwd: String) {
        this.email = email
        this.passwd = passwd
        
        vmCoroutineScope.launch {
            userRepository.getUserByEmail(this@LoginViewModel, email)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}