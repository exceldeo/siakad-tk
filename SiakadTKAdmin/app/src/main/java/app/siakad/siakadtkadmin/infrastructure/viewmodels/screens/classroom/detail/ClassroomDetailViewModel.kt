package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClassroomDetailViewModel(private val context: Context) :
  ViewModel(), ClassroomDetailListener {
  private val userListLiveData = MutableLiveData<ArrayList<PenggunaModel>>()
  private val userRepository = UserRepository()

  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  private val dataPenggunaList = arrayListOf<PenggunaModel>()
  private val dataPenggunaKeyList = mutableSetOf<String>()

  fun setUsers(users: ArrayList<String>) {
    if (dataPenggunaList.isEmpty()) {
      vmCoroutineScope.launch {
        users.forEach {
          userRepository.getUserById(this@ClassroomDetailViewModel, it)
        }
      }
    }
  }

  override fun setUser(pengguna: ModelContainer<PenggunaModel>) {
    if (pengguna.status == ModelState.SUCCESS) {
      if (pengguna.data != null) {
        if (!dataPenggunaKeyList.contains(pengguna.data?.userId!!)) {
          dataPenggunaKeyList.add(pengguna.data?.userId!!)
          dataPenggunaList.add(pengguna.data!!)
          userListLiveData.postValue(dataPenggunaList)
        }
      }
    } else if (pengguna.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  fun getUserList(): LiveData<ArrayList<PenggunaModel>> {
    return userListLiveData
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}