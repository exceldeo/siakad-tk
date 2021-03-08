package app.siakad.siakadtkadmin.infrastructure.viewmodels.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository

class MainViewModel : ViewModel() {
    private val _text = MutableLiveData<ModelContainer<String>>()
    private val authRepository = AuthenticationRepository()

    fun logout() {
        authRepository.logout()
    }
}