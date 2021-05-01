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
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.BasketRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.basket.BasketListener
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KeranjangViewModel(private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel(), BasketListener{
    private val basketRepository = BasketRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    init {
        vmCoroutineScope.launch {
            basketRepository.initEventListener()
        }
    }

    fun insertItemBasket(name: String, image: String, ukuran: String = "", jumlah: Int, harga: Int) {
        vmCoroutineScope.launch {
            basketRepository.addItem(this@KeranjangViewModel, DetailKeranjangModel(nama = name, gambar = image, ukuran = ukuran, jumlah = jumlah, harga = harga))
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun notifyInsertDataStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else {
            showToast(context.getString(R.string.fail_set_data))
        }
    }
}