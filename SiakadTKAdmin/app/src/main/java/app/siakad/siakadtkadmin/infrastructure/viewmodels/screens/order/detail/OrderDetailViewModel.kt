package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailKeranjangModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.repositories.OrderRepository
import app.siakad.siakadtkadmin.domain.repositories.ProductRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderDetailViewModel(private val context: Context) :
  ViewModel(), OrderDetailListener {
  private val orderRepository = OrderRepository()
  private val productRepository = ProductRepository()

  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
  private val dataProdukList = arrayListOf<Any>()
  private val dataAcceptedMapList = mutableMapOf<String, DetailKeranjangModel>()

  fun getProductList(products: ArrayList<DetailKeranjangModel>) {
    vmCoroutineScope.launch {
      products.forEach {
        dataAcceptedMapList[it.produkId] = it

        if (it.ukuran != "") {
          productRepository.initGetUniformListById(this@OrderDetailViewModel, it.produkId)
        } else {
          productRepository.initGetBookListById(this@OrderDetailViewModel, it.produkId)
        }
      }
    }
  }

  override fun notifyOrderChangeStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_set_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_set_data))
    }
  }

  override fun notifyOrderDeleteStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_del_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_del_data))
    }
  }

  override fun addBukuItem(buku: ModelContainer<BukuModel>) {
    if (buku.status == ModelState.SUCCESS) {
      if (buku.data != null) {
        dataProdukList.add(buku.data!!)
      }
    }
  }

  override fun updateBukuItem(buku: ModelContainer<BukuModel>) {
    if (buku.status == ModelState.SUCCESS) {
      if (buku.data != null) {
        dataProdukList.add(buku.data!!)
        dataProdukList.forEachIndexed { index, item ->
          if (item is BukuModel) {
            if (item.produkId == buku.data?.produkId) {
              dataProdukList[index] = buku.data!!
            }
          }
        }
      }
    }
  }

  override fun removeBukuItem(buku: ModelContainer<BukuModel>) {
    if (buku.status == ModelState.SUCCESS) {
      if (buku.data != null) {
        var target = 0
        dataProdukList.forEachIndexed feData@{ index, item ->
          if (item is BukuModel) {
            if (item.produkId == buku.data?.produkId) {
              target = index
              return@feData
            }
          }
        }
        dataProdukList.removeAt(target)
      }
    }
  }

  override fun addSeragamItem(seragam: ModelContainer<SeragamModel>) {
    if (seragam.status == ModelState.SUCCESS) {
      if (seragam.data != null) {
        dataProdukList.add(seragam.data!!)
      }
    }
  }

  override fun updateSeragamItem(seragam: ModelContainer<SeragamModel>) {
    if (seragam.status == ModelState.SUCCESS) {
      if (seragam.data != null) {
        dataProdukList.add(seragam.data!!)
        dataProdukList.forEachIndexed { index, item ->
          if (item is SeragamModel) {
            if (item.produkId == seragam.data?.produkId) {
              dataProdukList[index] = seragam.data!!
            }
          }
        }
      }
    }
  }

  override fun removeSeragamItem(seragam: ModelContainer<SeragamModel>) {
    if (seragam.status == ModelState.SUCCESS) {
      if (seragam.data != null) {
        var target = 0
        dataProdukList.forEachIndexed feData@{ index, item ->
          if (item is SeragamModel) {
            if (item.produkId == seragam.data?.produkId) {
              target = index
              return@feData
            }
          }
        }
        dataProdukList.removeAt(target)
      }
    }
  }

  fun updateDataToAccepted(pesanan: PesananModel, status: String) {
    pesanan.statusPesan = status
    vmCoroutineScope.launch {
      orderRepository.updateOrderData(this@OrderDetailViewModel, pesanan)
    }

    revertProductCount(pesanan.detailPesanan!!)
  }

  fun removeData(pesanan: PesananModel) {
    vmCoroutineScope.launch {
      orderRepository.removeOrderData(this@OrderDetailViewModel, pesanan)
    }

    revertProductCount(arrayListOf())
  }

  fun revertProductCount(dataAccepted: ArrayList<DetailKeranjangModel>) {
    val dataAcceptedKeyList = mutableSetOf<String>()

    vmCoroutineScope.launch {
      dataAccepted.forEach {
        dataAcceptedKeyList.add(it.produkId)
      }

      dataProdukList.forEach {
        if (it is SeragamModel) {
          if (!dataAcceptedKeyList.contains(it.produkId)) {
            val data = dataAcceptedMapList[it.produkId]
            val newData = it

            newData.detailSeragam.forEachIndexed forE@{ index, detailSeragamModel ->
              if (detailSeragamModel.ukuran == data?.ukuran) {
                newData.detailSeragam[index].jumlah += data?.jumlah
                return@forE
              }
            }

            productRepository.updateDataSeragam(this@OrderDetailViewModel, newData)
          }
        } else if (it is BukuModel) {
          if (!dataAcceptedKeyList.contains(it.produkId)) {
            val data = dataAcceptedMapList[it.produkId]
            val newData = it

            newData.jumlah += data?.jumlah!!

            productRepository.updateDataBuku(this@OrderDetailViewModel, newData)
          }
        }
      }
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}