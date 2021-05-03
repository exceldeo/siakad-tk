package app.siakad.siakadtkadmin.presentation.screens.user.helper

import app.siakad.siakadtkadmin.domain.models.PenggunaModel

interface UserClickHelper {
    fun navigateToUserDetail(siswa: PenggunaModel)
}