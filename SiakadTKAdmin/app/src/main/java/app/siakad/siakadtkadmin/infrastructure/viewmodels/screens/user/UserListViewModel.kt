package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserListViewModel(private val context: Context) :
    ViewModel(),
    UserListListener {
    private val userListLiveData = MutableLiveData<ArrayList<Siswa>>()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val dataPenggunaList = arrayListOf<Siswa>()

    fun setUserType(verified: Boolean) {
        if (dataPenggunaList.isEmpty()) {
            vmCoroutineScope.launch {
                userRepository.initGetUserListListener(this@UserListViewModel, verified)
            }
        }
    }

    override fun setUserList(penggunaList: ModelContainer<ArrayList<PenggunaModel>>) {
        if (penggunaList.status == ModelState.SUCCESS) {
            if (penggunaList.data?.isNotEmpty()!!) {
                penggunaList.data?.forEach { user ->
                    dataPenggunaList.add(
                        Siswa(
                            nama = user.nama,
                            noHP = user.noHP,
                            email = user.email,
                            passwd = user.passwd,
                            alamat = user.alamat,
                            userId = user.userId,
                            status = user.status
                        )
                    )
                    userListLiveData.postValue(dataPenggunaList)
                }
                showToast(context.getString(R.string.scs_get_data))
            }
        } else if (penggunaList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    fun getUserList() : LiveData<ArrayList<Siswa>> {
        return userListLiveData
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}