package app.siakad.siakadtkadmin.domain.utils.listeners.user

import app.siakad.siakadtkadmin.domain.models.DetailPenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface UserDetailListener {
    fun setUserDetail(detail: ModelContainer<DetailPenggunaModel>)
    fun notifyUserDetailChange(status: ModelContainer<String>)
}