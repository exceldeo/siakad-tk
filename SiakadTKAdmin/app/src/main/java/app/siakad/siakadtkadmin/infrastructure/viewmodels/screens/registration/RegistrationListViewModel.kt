package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.registration

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.RegistrationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationListListener
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationListViewModel(private val context: Context) :
    ViewModel(),
    RegistrationListListener {
    private val registrationListLiveData = MutableLiveData<ArrayList<DaftarUlang>>()

    private val registrationRepository = RegistrationRepository()
    private val userRepository = UserRepository()

    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private val resgistrationModelList = arrayListOf<DaftarUlangModel>()
    private val resgistrationList = arrayListOf<DaftarUlang>()

    fun setRegistrationType(verified: Boolean) {
        vmCoroutineScope.launch {
            registrationRepository.initGetRegistrationListListener(
                this@RegistrationListViewModel,
                verified
            )
        }
    }

    override fun setRegistrationList(dafulList: ModelContainer<ArrayList<DaftarUlangModel>>) {
        if (dafulList.status == ModelState.SUCCESS) {
            if (dafulList.data?.isNotEmpty()!!) {
                resgistrationModelList.addAll(dafulList.data!!)
                resgistrationModelList.forEach {
                    vmCoroutineScope.launch {
                        userRepository.getUserById(this@RegistrationListViewModel, it.userId)
                    }
                }
            }
        } else if (dafulList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

    override fun setUser(user: ModelContainer<PenggunaModel>) {
        if (user.status == ModelState.SUCCESS) {
            if (user.data != null) {
                resgistrationModelList.forEach forE@{
                    if (it.userId == user.data?.userId) {
                        resgistrationList.add(
                            DaftarUlang(user.data!!, it)
                        )
                        registrationListLiveData.postValue(resgistrationList)
                        return@forE
                    }
                }
            }
        } else if (user.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

    fun getRegistrationList(): LiveData<ArrayList<DaftarUlang>> {
        return registrationListLiveData
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}