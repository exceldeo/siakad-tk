package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.repositories.OrderRepository
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val orderList = MutableLiveData<ArrayList<Pesanan>>()
    private val orderRepository = OrderRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var orderRepoObserver: Observer<ModelContainer<ArrayList<PesananModel>>>

    init {
        vmCoroutineScope.launch {
            orderRepository.initEventListener()
        }
        setupObserver()
    }

    private fun setupObserver() {
        orderRepoObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                val dataRepo = arrayListOf<Pesanan>()
                val list = data.data

                list?.forEach { item ->
                    dataRepo.add(
                        Pesanan(
//                            pesananId = item.pesananId,
//                            detailPesanan = item.detailPesanan,
//                            keterangan = item.keterangan,
//                            tanggal = item.tanggal
                        )
                    )
                }
                orderList.postValue(dataRepo)
                showToast(context.getString(R.string.scs_get_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_data))
            }
        }

        orderRepository.getOrderList().observe(lcOwner, orderRepoObserver)
    }

    fun getOrderList(): LiveData<ArrayList<Pesanan>> {
        return orderList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}