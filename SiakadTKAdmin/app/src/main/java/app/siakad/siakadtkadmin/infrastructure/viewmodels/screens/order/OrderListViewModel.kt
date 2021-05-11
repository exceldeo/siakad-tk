package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.repositories.OrderRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderListListener
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderListViewModel(private val context: Context) :
  ViewModel(), OrderListListener {
  private val orderList = MutableLiveData<ArrayList<Pesanan>>()

  private val orderRepository = OrderRepository()
  private val userRepository = UserRepository()

  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  private var dataPesananModelList: ArrayList<PesananModel> = arrayListOf()
  private var dataPesananList: ArrayList<Pesanan> = arrayListOf()

  fun setOrderType(type: String) {
    if (dataPesananList.isEmpty()) {
      vmCoroutineScope.launch {
        orderRepository.initGetOrderEventListener(this@OrderListViewModel, type)
      }
    }
  }

  override fun addOrderItem(order: ModelContainer<PesananModel>) {
    if (order.status == ModelState.SUCCESS) {
      if (order.data != null) {
        dataPesananModelList.add(order.data!!)
        vmCoroutineScope.launch {
          userRepository.getUserById(this@OrderListViewModel, order.data?.userId!!)
        }
      }
    } else if (order.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun updateOrderItem(order: ModelContainer<PesananModel>) {
    if (order.status == ModelState.SUCCESS) {
      if (order.data != null) {
        var target = 0
        var isRemoved = false
        dataPesananList.forEachIndexed forE@{ index, item ->
          if (item.pesanan.pesananId == order.data?.pesananId!!) {
            if (item.pesanan.statusPesan != order.data?.statusPesan) {
              isRemoved = true
              target = index
            } else {
              dataPesananList[index].pesanan = order.data!!
            }
            return@forE
          }
        }
        if (isRemoved) {
          dataPesananList.removeAt(target)
        }
        orderList.postValue(dataPesananList)
      }
    } else if (order.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun removeOrderItem(order: ModelContainer<PesananModel>) {
    if (order.status == ModelState.SUCCESS) {
      if (order.data != null) {
        var target = 0
        dataPesananList.forEachIndexed forE@{ index, item ->
          if (item.pesanan.pesananId == order.data?.pesananId!!) {
            target = index
            return@forE
          }
        }
        dataPesananList.removeAt(target)
        orderList.postValue(dataPesananList)
      }
    } else if (order.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun setUser(user: ModelContainer<PenggunaModel>) {
    if (user.status == ModelState.SUCCESS) {
      if (user.data != null) {
        dataPesananModelList.forEach forE@{
          if (it.userId == user.data?.userId) {
            dataPesananList.add(
              Pesanan(user.data!!, it)
            )
            orderList.postValue(dataPesananList)
            return@forE
          }
        }
      }
    } else if (user.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  fun getOrderLIst(): LiveData<ArrayList<Pesanan>> {
    return orderList
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}