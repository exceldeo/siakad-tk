package app.siakad.siakadtkadmin.domain.repositories

import android.content.Context

class UserRepository(private val context: Context) {
    companion object {
        val userDB = FirebaseRef(
            MainRepository.USER_REF
        ).getRef()
    }
}