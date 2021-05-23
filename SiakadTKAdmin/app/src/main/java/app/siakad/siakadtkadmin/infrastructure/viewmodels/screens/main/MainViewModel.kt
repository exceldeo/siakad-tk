package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.main.MainListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context) : ViewModel(), MainListener {
  private val authRepository = AuthenticationRepository()
  private val userRepository = UserRepository()

  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  private val dataPenggunaList = arrayListOf<PenggunaModel>()

  fun logout() {
    authRepository.logout()
  }

  fun resetDaftarUlang() {
    vmCoroutineScope.launch {
      userRepository.initGetUserListListener(this@MainViewModel)
    }
  }

  override fun addUserItem(pengguna: ModelContainer<PenggunaModel>) {
    if (pengguna.status == ModelState.SUCCESS) {
      if (pengguna.data != null) {
        val pengguna = pengguna.data!!
        pengguna.detailPengguna?.dafulState = false
        vmCoroutineScope.launch {
          userRepository.updateUserData(this@MainViewModel, pengguna)
        }
      }
    } else if (pengguna.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun notifyUserDetailChangeStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_set_data))
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}