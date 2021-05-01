package app.siakad.siakadtkadmin.presentation.screens.user.listener

import app.siakad.siakadtkadmin.domain.models.PenggunaModel

interface UserClickListener {
    fun navigateToUserDetail(siswa: PenggunaModel)
}