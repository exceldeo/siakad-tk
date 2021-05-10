package app.siakad.siakadtkadmin.domain.utils.listeners.registration

import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface RegistrationListListener {
    fun addRegistrationItem(daful: ModelContainer<DaftarUlangModel>)
    fun setUser(user: ModelContainer<PenggunaModel>)
}