package app.siakad.siakadtk.infrastructure.viewmodels.screens.basket

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.product.BukuModel
import app.siakad.siakadtk.domain.models.product.DetailSeragamModel
import app.siakad.siakadtk.domain.models.product.SeragamModel
import app.siakad.siakadtk.domain.repositories.BasketRepository
import app.siakad.siakadtk.domain.repositories.OrderRepository
import app.siakad.siakadtk.domain.repositories.ProductRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.basket.BasketListener
import app.siakad.siakadtk.domain.utils.listeners.order.OrderListener
import app.siakad.siakadtk.infrastructure.data.DetailKeranjang
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.infrastructure.data.product.Seragam
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KeranjangViewModel(private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel(), BasketListener, OrderListener, ProductListListener{
    private val basketList = MutableLiveData<ArrayList<DetailKeranjangModel>>()
    private val dataKeranjang = arrayListOf<DetailKeranjangModel>()

    private val dataSeragamList = arrayListOf<SeragamModel>()
    private val dataBukuList = arrayListOf<BukuModel>()

    private val basketRepository = BasketRepository()
    private val orderRepository = OrderRepository()
    private val productRepository = ProductRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    init {
        vmCoroutineScope.launch {
            basketRepository.initEventListener(this@KeranjangViewModel)
        }
        vmCoroutineScope.launch {
            productRepository.initGetBookEventListener(this@KeranjangViewModel)
            productRepository.initGetUniformEventListener(this@KeranjangViewModel)
        }
    }

    fun insertItemBasket(name: String, image: String, ukuran: String = "", jumlah: Int, harga: Int, produkId: String = "", adminId: String= "") {
        vmCoroutineScope.launch {
            basketRepository.addItem(this@KeranjangViewModel, DetailKeranjangModel(
                produkId = produkId,
                nama = name,
                gambar = image,
                ukuran = ukuran,
                jumlah = jumlah,
                harga = harga,
                adminId = adminId,
            ))
        }
    }

    fun insertBasketToOrder(data: ArrayList<DetailKeranjangModel>) {
        vmCoroutineScope.launch {
            orderRepository.insertDataPesanan(this@KeranjangViewModel, data)
            for (item in data) {
                var find = false
                for (buku in dataBukuList) {
                    if (item.nama == buku.namaProduk) {
                        var updateProduct = buku
                        updateProduct.jumlah = buku.jumlah - item.jumlah
                        productRepository.updateDataBuku(this@KeranjangViewModel, updateProduct)
                        break
                    }
                }
                if(!find) {
                    for (seragam in dataSeragamList) {
                        if (item.nama == seragam.namaProduk) {
                            var updateProduct = seragam
                            var updateDetail = arrayListOf<DetailSeragamModel>()
                            updateDetail.addAll(seragam.detailSeragam)
                            for (type in updateDetail.withIndex())
                            {
                                if(item.ukuran == type.value.ukuran){
                                    updateDetail[type.index].jumlah = type.value.jumlah - item.jumlah
                                    updateProduct.jumlah = seragam.jumlah - item.jumlah
                                    break
                                }
                            }
                            updateProduct.detailSeragam.clear()
                            updateProduct.detailSeragam.addAll(updateDetail)
                            productRepository.updateDataSeragam(this@KeranjangViewModel, updateProduct)
                            break
                        }
                    }
                }
            }
            basketRepository.resetKeranjang(this@KeranjangViewModel)
        }
    }

    override fun setBasketList(basket: ModelContainer<ArrayList<DetailKeranjangModel>>) {
        if (basket.status == ModelState.SUCCESS) {
            if (basket.data?.isNotEmpty()!!) {
                basket.data?.forEach { item ->
                    dataKeranjang.add(
                        DetailKeranjangModel(
                            adminId = item.adminId,
                            produkId = item.produkId,
                            nama = item.nama,
                            gambar = item.gambar,
                            jumlah = item.jumlah,
                            harga = item.harga,
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
                        DetailKeranjangModel(
                            nama = item.nama,
                            gambar = item.gambar,
                            jumlah = item.jumlah,
                            harga = item.harga,
                            ukuran = item.ukuran,
                            adminId = item.adminId,
                            produkId = item.produkId
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
        if (basket.status == ModelState.SUCCESS) {
            dataKeranjang.clear()
            basketList.postValue(dataKeranjang)
        } else if (basket.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

    fun getBasketList(): LiveData<ArrayList<DetailKeranjangModel>> {
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

    override fun notifyUpdateDataStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_update_data))
        } else {
            showToast(context.getString(R.string.fail_update_data))
        }
    }

    override fun notifyOrderChangeStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

    override fun setUniformList(product: ModelContainer<ArrayList<SeragamModel>>) {
        if (product.status == ModelState.SUCCESS) {
            if (product.data?.isNotEmpty()!!) {
                product.data?.forEach { item ->
                    dataSeragamList.add(
                        SeragamModel(
                            produkId = item.produkId,
                            namaProduk = item.namaProduk,
                            jenisKelamin = item.jenisKelamin,
                            jumlah = item.jumlah,
                            detailSeragam = item.detailSeragam,
                            fotoProduk = item.fotoProduk,
                            adminId = item.adminId
                        )
                    )
                }
            }
        } else if (product.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    override fun setBookList(product: ModelContainer<ArrayList<BukuModel>>) {
        if (product.status == ModelState.SUCCESS) {
            if (product.data?.isNotEmpty()!!) {
                product.data?.forEach { item ->
                    dataBukuList.add(
                        BukuModel(
                            produkId = item.produkId,
                            namaProduk = item.namaProduk,
                            jumlah = item.jumlah,
                            fotoProduk = item.fotoProduk,
                            adminId = item.adminId
                        )
                    )
                }
            }
        } else if (product.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }
}