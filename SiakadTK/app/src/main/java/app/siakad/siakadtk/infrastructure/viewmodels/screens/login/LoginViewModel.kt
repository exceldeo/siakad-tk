package app.siakad.siakadtk.infrastructure.viewmodels.screens.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.utils.listeners.login.LoginListener
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel (private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel(), LoginListener {
    private val authRepository = AuthenticationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var email: String = ""
    private var passwd: String = ""
    private var userId: String = ""
    private var userStatus: Boolean = false
    private var pengguna = PenggunaModel()

    fun loginSiswa(email: String, passwd: String) {
        this.email = email
        this.passwd = passwd

        vmCoroutineScope.launch {
            userRepository.getUserByEmail(this@LoginViewModel, email)
        }
    }

    override fun setUser(user: ModelContainer<PenggunaModel>) {
        if (user.status == ModelState.SUCCESS) {
            val item = user.data

            showToast("ngeset")
            if (item != null) {
                (context as AuthenticationListener).getAccountStatus(false)
                if (item.role == UserRoleModel.SISWA.str) {
                    userId = item.userId
                    userStatus = item.status
                    pengguna = PenggunaModel(
                        nama = item.nama,
                        email = item.email,
                        passwd = item.passwd,
                        status = item.status
                    )
                    AuthenticationRepository.setUser(userId, email, passwd, item.status)
                    authRepository.login(this@LoginViewModel, email, passwd)
                } else {
                    showToast(context.getString(R.string.fail_login_not_siswa))
                }
            } else if (user.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_user))
                (context as AuthenticationListener).getAccountStatus(true)
            } else {
                    showToast("masuk")
                (context as AuthenticationListener).getAccountStatus(true)
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun notifyLoginStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_login))

            if (!AuthenticationRepository.userState) {
                AuthenticationRepository.setUser(userId, email, passwd, userStatus)
            }

            if(pengguna.status) (context as AuthenticationListener).navigateToMain()
            else (context as AuthenticationListener).navigateToPendingMain()
        } else {
            showToast(context.getString(R.string.fail_login))
        }
    }

    override fun notifyMakeKeranjangStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_basket))
        } else {
            showToast(context.getString(R.string.fail_basket))
        }
    }
}