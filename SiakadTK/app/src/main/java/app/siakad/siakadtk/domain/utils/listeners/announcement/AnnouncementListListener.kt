package app.siakad.siakadtkadmin.domain.utils.listeners.announcement

import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface AnnouncementListListener {
    fun setAnnouncementList(pengumumanList: ModelContainer<ArrayList<PengumumanModel>>)
}