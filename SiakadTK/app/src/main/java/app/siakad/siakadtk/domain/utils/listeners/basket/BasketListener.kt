package app.siakad.siakadtk.domain.utils.listeners.basket

import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface BasketListener {
    fun notifyInsertDataStatus(status: ModelContainer<String>)
}