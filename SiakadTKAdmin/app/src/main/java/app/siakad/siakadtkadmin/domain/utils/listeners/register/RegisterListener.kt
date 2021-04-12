package app.siakad.siakadtkadmin.domain.utils.listeners.register

import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface RegisterListener {
    fun notifyRegisterStatus(status: ModelContainer<String>)
    fun notifyDataInsertStatus(status: ModelContainer<String>)
}