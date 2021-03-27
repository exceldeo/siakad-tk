package app.siakad.siakadtkadmin.domain.utils.listeners.user

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface UserListListener {
    fun setUserList(penggunaList: ModelContainer<ArrayList<PenggunaModel>>)
}