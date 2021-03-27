package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.repositories.AuthenticationRepository

class MainViewModel : ViewModel() {
    private val _text = MutableLiveData<ModelContainer<String>>()
    private val authRepository = AuthenticationRepository()

    fun logout() {
        authRepository.logout()
    }
}