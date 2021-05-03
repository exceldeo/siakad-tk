package app.siakad.siakadtk.domain.utils.listeners.register

import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface RegisterListener {
    fun notifyRegisterStatus(status: ModelContainer<String>)
    fun notifyDataInsertStatus(status: ModelContainer<String>)
}