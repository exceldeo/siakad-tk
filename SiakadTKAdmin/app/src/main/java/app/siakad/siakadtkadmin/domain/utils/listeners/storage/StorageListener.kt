package app.siakad.siakadtkadmin.domain.utils.listeners.storage

import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface StorageListener {
    fun notifyUploadStatus(status: ModelContainer<String>)
    fun notifyDeleteStatus(status: ModelContainer<String>)
}