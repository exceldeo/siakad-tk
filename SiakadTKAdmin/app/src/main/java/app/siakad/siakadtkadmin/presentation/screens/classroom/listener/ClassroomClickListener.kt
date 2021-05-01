package app.siakad.siakadtkadmin.presentation.screens.classroom.listener

import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.infrastructure.data.Kelas

interface ClassroomClickListener {
    fun navigateToClassroomDetail(kelas: KelasModel)
}