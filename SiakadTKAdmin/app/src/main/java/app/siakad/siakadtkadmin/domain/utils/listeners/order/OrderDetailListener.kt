package app.siakad.siakadtkadmin.domain.utils.listeners.order

import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface OrderDetailListener {
  fun notifyOrderChangeStatus(status: ModelContainer<String>)
  fun notifyOrderDeleteStatus(status: ModelContainer<String>)
}