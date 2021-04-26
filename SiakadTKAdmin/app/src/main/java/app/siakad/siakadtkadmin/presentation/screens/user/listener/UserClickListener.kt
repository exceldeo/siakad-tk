package app.siakad.siakadtkadmin.presentation.screens.user.listener

import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.infrastructure.data.Siswa

interface UserClickListener {
    fun navigateToUserDetail(siswa: PenggunaModel)
}