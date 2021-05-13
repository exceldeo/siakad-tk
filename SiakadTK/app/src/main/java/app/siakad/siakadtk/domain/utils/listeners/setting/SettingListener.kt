package app.siakad.siakadtk.domain.utils.listeners.setting

import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface SettingListener {
    fun notifyUserDetailPasswordStatus(status: ModelContainer<String>)
}