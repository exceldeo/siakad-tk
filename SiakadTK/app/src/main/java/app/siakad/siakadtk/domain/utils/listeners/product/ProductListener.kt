package app.siakad.siakadtkadmin.domain.utils.listeners.product

import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface ProductListener {
    fun notifyInsertDataStatus(status: ModelContainer<String>)
}