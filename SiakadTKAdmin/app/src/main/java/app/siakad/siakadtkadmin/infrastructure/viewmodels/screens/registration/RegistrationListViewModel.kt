package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.registration

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationListListener
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.infrastructure.data.Pengguna
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationListViewModel(private val context: Context) :
  ViewModel(), RegistrationListListener {
  private val registrationListLiveData = MutableLiveData<ArrayList<DaftarUlang>>()

  private val registrationRepository = RegistrationRepository()
  private val userRepository = UserRepository()
  private val classroomRepository = ClassroomRepository()

  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  private val resgistrationModelList = arrayListOf<DaftarUlangModel>()
  private val resgistrationList = arrayListOf<DaftarUlang>()
  private val registrationKeyList = mutableSetOf<String>()
  private val userKeyList = mutableSetOf<String>()

  fun setRegistrationType(verified: Boolean) {
    if (resgistrationModelList.isEmpty()) {
      vmCoroutineScope.launch {
        registrationRepository.initGetRegistrationListListener(
          this@RegistrationListViewModel,
          verified
        )
      }
    }
  }

  override fun addRegistrationItem(daful: ModelContainer<DaftarUlangModel>) {
    if (daful.status == ModelState.SUCCESS) {
      if (daful.data != null) {
        resgistrationModelList.add(daful.data!!)
        vmCoroutineScope.launch {
          userRepository.getUserById(this@RegistrationListViewModel, daful.data?.userId!!)
        }
      }
    } else if (daful.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun updateRegistrationItem(daful: ModelContainer<DaftarUlangModel>) {
    if (daful.status == ModelState.SUCCESS) {
      if (daful.data != null) {
        resgistrationList.forEachIndexed { index, item ->
          if (item.daful.dafulId == daful.data?.dafulId) {
            resgistrationList[index].daful = daful.data!!
          }
        }
        registrationListLiveData.postValue(resgistrationList)
      }
    } else if (daful.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun removeRegistrationItem(daful: ModelContainer<DaftarUlangModel>) {
    if (daful.status == ModelState.SUCCESS) {
      if (daful.data != null) {
        var target = 0
        resgistrationList.forEachIndexed feData@{ index, item ->
          if (item.daful.dafulId == daful.data?.dafulId) {
            target = index
            return@feData
          }
        }
        resgistrationList.removeAt(target)
        registrationListLiveData.postValue(resgistrationList)
      }
    } else if (daful.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun setUser(user: ModelContainer<PenggunaModel>) {
    if (user.status == ModelState.SUCCESS) {
      if (user.data != null) {
        resgistrationModelList.forEach forE@{
          if (it.userId == user.data?.userId && !registrationKeyList.contains(it.dafulId)) {
            registrationKeyList.add(it.dafulId)
            resgistrationList.add(DaftarUlang(Pengguna(user.data!!, KelasModel()), it))
            vmCoroutineScope.launch {
              classroomRepository.getClassById(
                this@RegistrationListViewModel,
                user.data?.detailPengguna?.kelasId!!
              )
            }
            return@forE
          }
        }
      }
    } else if (user.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_data))
    }
  }

  override fun setClass(kelas: ModelContainer<KelasModel>) {
    if (kelas.status == ModelState.SUCCESS) {
      if (kelas.data != null) {
        resgistrationList.forEachIndexed forE@{ index, daful ->
          if (daful.pengguna.pengguna.detailPengguna?.kelasId!! == kelas.data?.kelasId
            && !userKeyList.contains(daful.pengguna.pengguna.userId)
          ) {
            userKeyList.add(daful.pengguna.pengguna.userId)
            resgistrationList[index].pengguna.kelas = kelas.data!!
            registrationListLiveData.postValue(resgistrationList)
            return@forE
          }
        }
      }
    } else if (kelas.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_data))
    }
  }

  fun getRegistrationList(): LiveData<ArrayList<DaftarUlang>> {
    return registrationListLiveData
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }

  fun clearListener() {
    registrationRepository.removeEventListener()
    userRepository.removeEventListener()
    classroomRepository.removeEventListener()
  }
}