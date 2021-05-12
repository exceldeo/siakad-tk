package app.siakad.siakadtk.domain.utils.listeners.order

import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface OrderListener {
    fun notifyInsertDataStatus(status: ModelContainer<String>)
    fun notifyOrderChangeStatus(status: ModelContainer<String>)
}