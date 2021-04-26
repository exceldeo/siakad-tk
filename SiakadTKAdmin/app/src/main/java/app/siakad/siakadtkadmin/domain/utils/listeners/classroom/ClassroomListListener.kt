package app.siakad.siakadtkadmin.domain.utils.listeners.classroom

import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface ClassroomListListener {
    fun setClassroomList(kelasLIst: ModelContainer<ArrayList<KelasModel>>)
}