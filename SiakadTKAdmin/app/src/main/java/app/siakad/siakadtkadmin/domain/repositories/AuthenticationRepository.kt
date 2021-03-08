package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.models.UserModel
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository {
    companion object {
        val fbAuth = FirebaseAuth.getInstance()

        lateinit var currentUser: UserModel
        var userState: Boolean = false

        fun setUser(userId: String, email: String, passwd: String) {
            currentUser = UserModel(
                userId = userId,
                email = email,
                passwd = passwd
            )
            userState = true
        }

        fun deleteUser() {
            fbAuth.signOut()
            userState = false
        }
    }
}