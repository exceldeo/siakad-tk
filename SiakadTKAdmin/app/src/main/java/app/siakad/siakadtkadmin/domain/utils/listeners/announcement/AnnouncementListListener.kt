package app.siakad.siakadtkadmin.domain.utils.listeners.announcement

import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface AnnouncementListListener {
    fun addAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>)
    fun updateAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>)
    fun removeAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>)
}