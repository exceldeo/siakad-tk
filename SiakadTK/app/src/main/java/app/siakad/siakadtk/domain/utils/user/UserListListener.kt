package app.siakad.siakadtk.domain.utils.listeners.user

import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface UserListListener {
    fun setUserList(penggunaList: ModelContainer<ArrayList<PenggunaModel>>)
}