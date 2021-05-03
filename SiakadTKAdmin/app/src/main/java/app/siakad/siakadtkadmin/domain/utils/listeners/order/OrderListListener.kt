package app.siakad.siakadtkadmin.domain.utils.listeners.order

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface OrderListListener {
  fun setOrderList(orderList: ModelContainer<ArrayList<PesananModel>>)
  fun setUser(user: ModelContainer<PenggunaModel>)
}