package app.siakad.siakadtkadmin.domain.utils.listeners.registration

import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface RegistrationListListener {
    fun addRegistrationItem(daful: ModelContainer<DaftarUlangModel>)
    fun updateRegistrationItem(daful: ModelContainer<DaftarUlangModel>)
    fun removeRegistrationItem(daful: ModelContainer<DaftarUlangModel>)

    fun setUser(user: ModelContainer<PenggunaModel>)
    fun setClass(kelas: ModelContainer<KelasModel>)
}