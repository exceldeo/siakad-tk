package app.siakad.siakadtkadmin.domain.utils.listeners.announcement

import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface AnnouncementListListener {
    fun setAnnouncementList(pengumumanList: ModelContainer<ArrayList<PengumumanModel>>)
}