package app.siakad.siakadtk.presentation.utils.listener

import app.siakad.siakadtkadmin.presentation.utils.listener.MainListener

interface AuthenticationListener : MainListener {
    fun navigateToMain()
    fun navigateToPendingMain()
    fun getAccountStatus(isRejected: Boolean)
}