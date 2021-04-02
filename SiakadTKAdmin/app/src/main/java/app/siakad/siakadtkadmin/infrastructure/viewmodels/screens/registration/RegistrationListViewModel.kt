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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationListViewModel(private val context: Context) :
    ViewModel(),
    RegistrationListListener {
    private val registrationListLiveData = MutableLiveData<ArrayList<DaftarUlang>>()
    private val resgistrationList = arrayListOf<DaftarUlang>()
    private val registrationRepository = RegistrationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

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
                dafulList.data?.forEach { registration ->
                    userRepository.getUserById(this@RegistrationListViewModel, registration.userId)
                }
                showToast(context.getString(R.string.scs_get_data))
            }
        } else if (dafulList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

    override fun addUser(user: ModelContainer<PenggunaModel>) {
        if (user.status == ModelState.SUCCESS) {
            if (user.data != null) {
                resgistrationList.add(
                    DaftarUlang(
                        userId = user.data!!.userId,
                        alamat = user.data!!.alamat,
                        namaSiswa = user.data!!.nama,
                        noHP = user.data!!.noHP
                    )
                )
                registrationListLiveData.postValue(resgistrationList)
                showToast(context.getString(R.string.scs_get_data))
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