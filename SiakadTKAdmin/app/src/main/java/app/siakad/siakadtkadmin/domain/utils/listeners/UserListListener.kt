package app.siakad.siakadtkadmin.domain.utils.listeners

import app.siakad.siakadtkadmin.domain.models.UserModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.infrastructure.data.Siswa

interface UserListListener {
    fun setUserList(userList: ModelContainer<ArrayList<UserModel>>)
}