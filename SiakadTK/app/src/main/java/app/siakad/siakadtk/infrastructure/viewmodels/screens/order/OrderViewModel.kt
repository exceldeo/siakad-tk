package app.siakad.siakadtk.infrastructure.viewmodels.screens.order

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.infrastructure.data.Pesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.repositories.OrderRepository
import app.siakad.siakadtk.domain.utils.listeners.order.OrderListListener

class OrderViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), OrderListListener {
    private val orderList = MutableLiveData<ArrayList<Pesanan>>()

    private val orderRepository = OrderRepository()

    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var dataPesananModelList: ArrayList<PesananModel> = arrayListOf()
    private var dataPesananList: ArrayList<Pesanan> = arrayListOf()

//    private lateinit var orderRepoObserver: Observer<ModelContainer<ArrayList<PesananModel>>>

    init {
        vmCoroutineScope.launch {
            orderRepository.initGetOrderEventListener(this@OrderViewModel)
        }
    }

//    private fun setupObserver() {
//        orderRepoObserver = Observer { data ->
//            if (data.status == ModelState.SUCCESS) {
//                val dataRepo = arrayListOf<Pesanan>()
//                val list = data.data
//
//                list?.forEach { item ->
//                    dataRepo.add(
//                        Pesanan(
//                            pesanan = item
//                        )
//                    )
//                }
//                orderList.postValue(dataRepo)
//                showToast(context.getString(R.string.scs_get_data))
//            } else if (data.status == ModelState.ERROR) {
//                showToast(context.getString(R.string.fail_get_data))
//            }
//        }
//
//        orderRepository.getOrderList().observe(lcOwner, orderRepoObserver)
//    }



    fun getOrderList(): LiveData<ArrayList<Pesanan>> {
        return orderList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun addOrderItem(order: ModelContainer<PesananModel>) {
        if (order.status == ModelState.SUCCESS) {
            if (order.data != null) {
                dataPesananModelList.add(order.data!!)
                dataPesananList.add(Pesanan(pesanan = order.data!!))
                orderList.postValue(dataPesananList)
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

    override fun setOrderList(order: ModelContainer<ArrayList<PesananModel>>) {
        if (order.status == ModelState.SUCCESS) {
            if (order.data?.isNotEmpty()!!) {
                order.data?.forEach { item ->
                    dataPesananList.add(
                        Pesanan(
                            pesanan = item
                        )
                    )
                    orderList.postValue(dataPesananList)
                }
            }
        } else if (order.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        orderRepository.removeEventListener()
//    }
}