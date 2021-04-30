package app.siakad.siakadtk.infrastructure.viewmodels.screens.basket

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.repositories.BasketRepository
import app.siakad.siakadtk.infrastructure.data.product.Buku
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KeranjangViewModel(private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel() {
    private val basketRepository = BasketRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val basketList = arrayListOf<DetailKeranjangModel>()

    fun insertItemBasket(name: String, image: String, ukuran: String, jumlah: Int, harga: Int) {
        vmCoroutineScope.launch {
            basketRepository.addItem(DetailKeranjangModel(nama = name, gambar = image, ukuran = ukuran, jumlah = jumlah, harga = harga))
        }
    }

    fun updateBasket() {

    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}