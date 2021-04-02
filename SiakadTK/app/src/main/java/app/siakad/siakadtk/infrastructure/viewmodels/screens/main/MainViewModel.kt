package app.siakad.siakadtk.infrastructure.viewmodels.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository

class MainViewModel : ViewModel() {
    private val _text = MutableLiveData<ModelContainer<String>>()
    private val authRepository = AuthenticationRepository()

    fun logout() {
        authRepository.logout()
    }
}