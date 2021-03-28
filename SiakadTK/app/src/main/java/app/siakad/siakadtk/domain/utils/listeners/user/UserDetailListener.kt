package app.siakad.siakadtkadmin.domain.utils.listeners.user

import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface UserDetailListener {
    fun setUserDetail(detail: ModelContainer<DetailPenggunaModel>)
    fun notifyUserDetailChange(status: ModelContainer<String>)
}