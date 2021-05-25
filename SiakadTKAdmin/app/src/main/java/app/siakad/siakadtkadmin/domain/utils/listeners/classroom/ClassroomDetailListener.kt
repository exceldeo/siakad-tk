package app.siakad.siakadtkadmin.domain.utils.listeners.classroom

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface ClassroomDetailListener {
  fun setUser(pengguna: ModelContainer<PenggunaModel>)
}