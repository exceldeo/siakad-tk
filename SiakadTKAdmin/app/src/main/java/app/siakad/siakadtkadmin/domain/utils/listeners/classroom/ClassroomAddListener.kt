package app.siakad.siakadtkadmin.domain.utils.listeners.classroom

import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface ClassroomAddListener {
    fun notifyClassroomAddStatus(status: ModelContainer<String>)
}