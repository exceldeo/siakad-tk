package app.siakad.siakadtk.domain.utils.listeners.storage

import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface StorageListener {
    fun notifyUploadStatus(status: ModelContainer<String>)
}