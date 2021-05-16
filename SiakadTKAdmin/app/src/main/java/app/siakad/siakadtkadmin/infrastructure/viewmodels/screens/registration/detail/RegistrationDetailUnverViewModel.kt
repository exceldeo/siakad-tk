package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.registration.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.RegistrationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationDetailListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationDetailUnverViewModel(private val context: Context) :
  ViewModel(), RegistrationDetailListener {
  private val regisRepository = RegistrationRepository()
  private val userRepository = UserRepository()
  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  private var user: PenggunaModel? = null

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }

  fun updateDataToVerified(daful: DaftarUlangModel, pengguna: PenggunaModel) {
    user = pengguna
    daful.statusDaful = true
    vmCoroutineScope.launch {
      regisRepository.updateRegisData(this@RegistrationDetailUnverViewModel, daful)
    }
  }

  override fun notifyRegistrationDetailChangeStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      if (user != null) {
        user?.detailPengguna?.dafulState = true
        vmCoroutineScope.launch {
          userRepository.updateUserData(this@RegistrationDetailUnverViewModel, user!!)
        }
      }
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_update_data))
    }
  }

  override fun notifyRegistrationDetailDeleteStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_del_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_del_data))
    }
  }

  override fun notifyUserDetailChangeStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_update_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_update_data))
    }
  }

  fun removeData(daful: DaftarUlangModel) {
    vmCoroutineScope.launch {
      regisRepository.removeRegisData(this@RegistrationDetailUnverViewModel, daful)
    }
  }
}