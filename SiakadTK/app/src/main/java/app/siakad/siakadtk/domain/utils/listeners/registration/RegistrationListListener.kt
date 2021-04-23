package app.siakad.siakadtk.domain.utils.listeners.registration

import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface RegistrationListListener {
    fun addUser(user: ModelContainer<PenggunaModel>)
}