package app.siakad.siakadtkadmin.domain.utils.listeners.user

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface UserListListener {
    fun addUserItem(pengguna: ModelContainer<PenggunaModel>)
    fun updateUserItem(pengguna: ModelContainer<PenggunaModel>)
    fun removeUserItem(pengguna: ModelContainer<PenggunaModel>)
}