package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.repositories.OrderRepository
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
  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

  override fun notifyOrderChangeStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_set_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_set_data))
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }

  fun updateDataToAccepted(pesanan: PesananModel) {
    pesanan.statusPesan = "Diproses"
    vmCoroutineScope.launch {
      orderRepository.updateOrderData(this@OrderDetailViewModel, pesanan)
    }
  }
}