package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.registration.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.ClassroomRepository
import app.siakad.siakadtkadmin.domain.repositories.RegistrationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationDetailListener
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationDetailUnverViewModel(private val context: Context) :
  ViewModel(), RegistrationDetailListener {
  private val regisRepository = RegistrationRepository()
  private val userRepository = UserRepository()
  private val classroomRepository = ClassroomRepository()

  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  private var user: PenggunaModel? = null
  private var kelas: KelasModel? = null

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }

  fun updateDataToVerified(daful: DaftarUlang) {
    val dafulItem = daful.daful
    dafulItem.statusDaful = true

    user = daful.pengguna.pengguna!!
    kelas = daful.pengguna.kelas!!
    vmCoroutineScope.launch {
      regisRepository.updateRegisData(this@RegistrationDetailUnverViewModel, dafulItem)
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
      vmCoroutineScope.launch {
        classroomRepository.updateClassroomUser(
          this@RegistrationDetailUnverViewModel,
          kelas!!,
          user?.userId!!
        )
      }
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_update_data))
    }
  }

  override fun notifyClassroomUserChangeStatus(status: ModelContainer<String>) {
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