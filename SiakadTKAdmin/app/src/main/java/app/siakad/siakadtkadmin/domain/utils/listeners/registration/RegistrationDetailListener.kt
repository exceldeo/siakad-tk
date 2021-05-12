package app.siakad.siakadtkadmin.domain.utils.listeners.registration

import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface RegistrationDetailListener {
  fun notifyRegistrationDetailChangeStatus(status: ModelContainer<String>)
  fun notifyRegistrationDetailDeleteStatus(status: ModelContainer<String>)
}