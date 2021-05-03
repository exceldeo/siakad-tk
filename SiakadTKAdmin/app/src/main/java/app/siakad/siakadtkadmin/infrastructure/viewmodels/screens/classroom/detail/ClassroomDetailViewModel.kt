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
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClassroomDetailViewModel(private val context: Context) :
    ViewModel(),
    UserListListener {
    private val userListLiveData = MutableLiveData<ArrayList<PenggunaModel>>()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val dataPenggunaList = arrayListOf<PenggunaModel>()

    fun setUserClassType(kelasId: String) {
        if (dataPenggunaList.isEmpty()) {
            vmCoroutineScope.launch {
                userRepository.initGetUserListByClassListener(this@ClassroomDetailViewModel, kelasId)
            }
        }
    }

    override fun setUserList(penggunaList: ModelContainer<ArrayList<PenggunaModel>>) {
        if (penggunaList.status == ModelState.SUCCESS) {
            if (penggunaList.data?.isNotEmpty()!!) {
                dataPenggunaList.clear()
                dataPenggunaList.addAll(penggunaList.data!!)
                userListLiveData.postValue(dataPenggunaList)
                showToast(context.getString(R.string.scs_get_data))
            }
        } else if (penggunaList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    fun getUserList() : LiveData<ArrayList<PenggunaModel>> {
        return userListLiveData
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}