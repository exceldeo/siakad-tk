package app.siakad.siakadtkadmin.presentation.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.data.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.DataModel

class SettingViewModel : ViewModel() {
    private val _text = MutableLiveData<DataModel<String>>()

    fun keluar() {
        AuthenticationRepository.deleteUser()
    }
}