package app.siakad.siakadtkadmin.infrastructure.viewmodels.main.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository
import app.siakad.siakadtkadmin.domain.ModelContainer

class SettingViewModel : ViewModel() {
    private val _text = MutableLiveData<ModelContainer<String>>()
    private val authRepository = AuthenticationRepository()

    fun logout() {
        authRepository.logout()
    }
}