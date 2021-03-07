package app.siakad.siakadtkadmin.data.repositories

import android.content.Context
import app.siakad.siakadtkadmin.data.db.FirebaseRef

class UserRepository(private val context: Context) {
    companion object {
        val userDB = FirebaseRef(MainRepository.USER_REF).getRef()
    }
}