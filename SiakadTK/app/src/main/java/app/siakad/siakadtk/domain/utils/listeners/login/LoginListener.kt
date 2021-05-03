package app.siakad.siakadtk.domain.utils.listeners.login

import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface LoginListener {
    fun setUser(user: ModelContainer<PenggunaModel>)
    fun notifyLoginStatus(status: ModelContainer<String>)
    fun notifyMakeKeranjangStatus(status: ModelContainer<String>)
}