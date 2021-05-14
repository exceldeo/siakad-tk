package app.siakad.siakadtk.domain.utils.listeners.order

import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface OrderListListener {
  fun addOrderItem(orderList: ModelContainer<PesananModel>)
  fun updateOrderItem(orderList: ModelContainer<PesananModel>)

  fun setOrderList(order: ModelContainer<ArrayList<PesananModel>>)
}