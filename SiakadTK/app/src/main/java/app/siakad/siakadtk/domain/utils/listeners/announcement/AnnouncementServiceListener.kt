package app.siakad.siakadtk.domain.utils.listeners.announcement

import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface AnnouncementServiceListener {
    fun sendAnnouncementNotification(pengumuman: ModelContainer<PengumumanModel>)
}