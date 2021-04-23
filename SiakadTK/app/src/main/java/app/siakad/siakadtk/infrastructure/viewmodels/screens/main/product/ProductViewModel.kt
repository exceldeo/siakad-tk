package app.siakad.siakadtk.infrastructure.viewmodels.screens.main.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Pemesanan"
    }
    val text: LiveData<String> = _text
}