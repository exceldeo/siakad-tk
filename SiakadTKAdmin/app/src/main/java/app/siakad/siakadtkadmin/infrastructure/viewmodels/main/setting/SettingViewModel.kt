package app.siakad.siakadtkadmin.infrastructure.viewmodels.main.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.infrastructure.DataContainer

class SettingViewModel : ViewModel() {
    private val _text = MutableLiveData<DataContainer<String>>()

    fun keluar() {
        AuthenticationRepository.deleteUser()
    }
}