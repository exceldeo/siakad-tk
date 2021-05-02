package app.siakad.siakadtk.infrastructure.viewmodels.screens.basket

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.repositories.BasketRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.basket.BasketListener
import app.siakad.siakadtk.infrastructure.data.DetailKeranjang
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KeranjangViewModel(private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel(), BasketListener{
    private val basketList = MutableLiveData<ArrayList<DetailKeranjang>>()
    private val dataKeranjang = arrayListOf<DetailKeranjang>()

    private val basketRepository = BasketRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    init {
        vmCoroutineScope.launch {
            basketRepository.initEventListener(this@KeranjangViewModel)
        }
    }

    fun insertItemBasket(name: String, image: String, ukuran: String = "", jumlah: Int, harga: Int) {
        vmCoroutineScope.launch {
            basketRepository.addItem(this@KeranjangViewModel, DetailKeranjangModel(nama = name, gambar = image, ukuran = ukuran, jumlah = jumlah, harga = harga))
        }
    }

    override fun setBasketList(basket: ModelContainer<ArrayList<DetailKeranjangModel>>) {
        if (basket.status == ModelState.SUCCESS) {
            if (basket.data?.isNotEmpty()!!) {
                basket.data?.forEach { item ->
                    dataKeranjang.add(
                        DetailKeranjang(
                            nama = item.nama,
                            gambar = item.gambar,
                            jumlah = item.jumlah,
                            ukuran = item.ukuran
                        )
                    )
                    basketList.postValue(dataKeranjang)
                }
            }
        } else if (basket.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

    override fun addBasketItem(basket: ModelContainer<ArrayList<DetailKeranjangModel>>) {
        if (basket.status == ModelState.SUCCESS) {
            if (basket.data?.isNotEmpty()!!) {
                basket.data?.forEach { item ->
                    dataKeranjang.add(
                        DetailKeranjang(
                            nama = item.nama,
                            gambar = item.gambar,
                            jumlah = item.jumlah,
                            ukuran = item.ukuran
                        )
                    )
                    basketList.postValue(dataKeranjang)
                }
            }
        } else if (basket.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

    override fun removeBasketItem(basket: ModelContainer<ArrayList<DetailKeranjangModel>>) {

    }

    fun getBasketList(): LiveData<ArrayList<DetailKeranjang>> {
        return basketList
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