package app.siakad.siakadtkadmin.domain.utils.listeners.login

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface LoginListener {
    fun setUer(user: ModelContainer<PenggunaModel>)
    fun notifyLoginStatus(status: ModelContainer<String>)
}