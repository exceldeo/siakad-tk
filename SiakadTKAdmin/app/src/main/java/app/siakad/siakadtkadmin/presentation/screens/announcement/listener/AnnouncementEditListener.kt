package app.siakad.siakadtkadmin.presentation.screens.announcement.listener

import app.siakad.siakadtkadmin.domain.models.PengumumanModel

interface AnnouncementEditListener {
    fun navigateToAnnouncementEdit(announecement: PengumumanModel)
}