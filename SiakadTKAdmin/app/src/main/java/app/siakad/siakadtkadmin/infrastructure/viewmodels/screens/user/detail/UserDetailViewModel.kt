package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailPenggunaModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.UserDetailRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserDetailViewModel(private val context: Context) :
    ViewModel(), UserDetailListener {
    private val userDetailLiveData = MutableLiveData<DetailPenggunaModel>()
    private val userDetailRepository = UserDetailRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    fun setUserId(userId: String) {
        vmCoroutineScope.launch {
            userDetailRepository.initGetUserDetailListener(this@UserDetailViewModel, userId)
        }
    }

    override fun setUserDetail(detail: ModelContainer<DetailPenggunaModel>) {
        if (detail.status == ModelState.SUCCESS) {
            userDetailLiveData.postValue(detail.data!!)
            showToast(context.getString(R.string.scs_get_data))
        } else if (detail.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    override fun notifyUserDetailChangeStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_set_data))
        }
    }
    
    fun getUserDetail(): LiveData<DetailPenggunaModel> {
        return userDetailLiveData
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}