package app.siakad.siakadtk.infrastructure.viewmodels.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.domain.ModelContainer
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository

class MainViewModel : ViewModel() {
    private val _text = MutableLiveData<ModelContainer<String>>()
    private val authRepository = AuthenticationRepository()

    fun logout() {
        authRepository.logout()
    }
}