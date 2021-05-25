package app.siakad.siakadtkadmin.domain.utils.listeners.announcement

import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface AnnouncementListListener {
    fun addAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>)
    fun updateAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>)
    fun removeAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>)
}