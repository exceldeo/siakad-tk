package app.siakad.siakadtk.infrastructure.viewmodels.screens.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Selamat Datang"
    }
    val text: LiveData<String> = _text
}