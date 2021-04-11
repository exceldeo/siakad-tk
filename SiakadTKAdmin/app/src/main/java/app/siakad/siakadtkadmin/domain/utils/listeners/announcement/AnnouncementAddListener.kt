package app.siakad.siakadtkadmin.domain.utils.listeners.announcement

import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface AnnouncementAddListener {
    fun notifyAnnouncementAddStatus(status: ModelContainer<String>)
}