package app.siakad.siakadtkadmin.domain.utils.listeners.order

import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface OrderDetailListener {
  fun notifyOrderChangeStatus(status: ModelContainer<String>)
  fun notifyOrderDeleteStatus(status: ModelContainer<String>)

  fun addBukuItem(buku: ModelContainer<BukuModel>)
  fun updateBukuItem(buku: ModelContainer<BukuModel>)
  fun removeBukuItem(buku: ModelContainer<BukuModel>)

  fun addSeragamItem(seragam: ModelContainer<SeragamModel>)
  fun updateSeragamItem(seragam: ModelContainer<SeragamModel>)
  fun removeSeragamItem(seragam: ModelContainer<SeragamModel>)
}