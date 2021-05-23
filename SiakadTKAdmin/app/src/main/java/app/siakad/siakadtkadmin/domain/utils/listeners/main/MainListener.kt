package app.siakad.siakadtkadmin.domain.utils.listeners.main

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface MainListener {
  fun addUserItem(pengguna: ModelContainer<PenggunaModel>)
  fun notifyUserDetailChangeStatus(status: ModelContainer<String>)
}