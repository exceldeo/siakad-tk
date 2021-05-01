package app.siakad.siakadtkadmin.domain.utils.listeners.announcement

import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface AnnouncementAddListener {
    fun notifyAnnouncementAddStatus(status: ModelContainer<String>)
    fun setUserById(pengguna: ModelContainer<PenggunaModel>)
    fun setClassById(kelas: ModelContainer<KelasModel>)
}