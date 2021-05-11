package app.siakad.siakadtkadmin.domain.utils.listeners.order

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface OrderListListener {
  fun addOrderItem(orderList: ModelContainer<PesananModel>)
  fun updateOrderItem(orderList: ModelContainer<PesananModel>)
  fun removeOrderItem(orderList: ModelContainer<PesananModel>)
  fun setUser(user: ModelContainer<PenggunaModel>)
}