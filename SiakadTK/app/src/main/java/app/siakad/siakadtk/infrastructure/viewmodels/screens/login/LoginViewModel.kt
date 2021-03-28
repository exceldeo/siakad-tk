package app.siakad.siakadtk.infrastructure.viewmodels.screens.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel (private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel() {
    private val authRepository = AuthenticationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var email: String = ""
    private var passwd: String = ""
    private var userId: String = ""

    private lateinit var loginObserver: Observer<ModelContainer<String>>
    private lateinit var penggunaObserver: Observer<ModelContainer<PenggunaModel>>

    init {
        setupObserver()
    }

    fun loginSiswa(email: String, passwd: String) {
        this.email = email
        this.passwd = passwd

        vmCoroutineScope.launch {
            userRepository.getUserByEmail(email)
        }
    }

    private fun setupObserver() {
        penggunaObserver = Observer { user ->
            if (user.status == ModelState.SUCCESS) {
                val item = user.data

                if (item != null) {
                    if (item.role == UserRoleModel.SISWA.str) {
                        if(authRepository.isEmailVerified()){
                            authRepository.login(email, passwd)
                            userId = item.userId
                        } else {
                            showToast(context.getString(R.string.email_is_not_verified))
                        }
                    } else {
                        showToast(context.getString(R.string.fail_login_not_siswa))
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

            userRepository.getUser().observe(lcOwner, penggunaObserver)
            authRepository.getAuthState().observe(lcOwner, loginObserver)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}