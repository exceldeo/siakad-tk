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

    init {
        if (dataPesananList.isEmpty()) {
            vmCoroutineScope.launch {
                orderRepository.initEventListener(this@OrderListViewModel)
            }
        }
    }

    override fun setOrderList(orderList: ModelContainer<ArrayList<PesananModel>>) {
        if (orderList.status == ModelState.SUCCESS) {
            if (orderList.data?.isNotEmpty()!!) {
                dataPesananModelList.clear()
                dataPesananModelList.addAll(orderList.data!!)
                dataPesananModelList.forEach {
                    vmCoroutineScope.launch {
                        userRepository.getUserById(this@OrderListViewModel, it.userId)
                    }
                }
            }
        } else if (orderList.status == ModelState.ERROR) {
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