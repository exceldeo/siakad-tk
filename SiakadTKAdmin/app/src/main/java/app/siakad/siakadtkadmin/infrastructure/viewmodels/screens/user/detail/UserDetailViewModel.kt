package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailPenggunaModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserDetailViewModel(private val context: Context) :
  ViewModel(), UserDetailListener {
  private val userRepository = UserRepository()
  private val authRepository = AuthenticationRepository()
  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  override fun notifyUserDetailChangeStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_set_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_set_data))
    }
  }

  override fun notifyUserDeleteStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {

      showToast(context.getString(R.string.scs_del_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_del_data))
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }

  fun updateDataToVerified(pengguna: PenggunaModel) {
    pengguna.status = true
    vmCoroutineScope.launch {
      userRepository.updateUserData(this@UserDetailViewModel, pengguna)
    }
  }

  fun removeData(pengguna: PenggunaModel) {
    vmCoroutineScope.launch {
      userRepository.removeUserData(this@UserDetailViewModel, pengguna)
    }
  }
}