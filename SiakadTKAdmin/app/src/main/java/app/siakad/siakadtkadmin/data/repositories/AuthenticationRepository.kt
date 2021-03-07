package app.siakad.siakadtkadmin.data.repositories

import app.siakad.siakadtkadmin.data.db.refs.User
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository {
    companion object {
        val fbAuth = FirebaseAuth.getInstance()

        lateinit var currentUser: User
        var userState: Boolean = false

        fun setUser(userId: String, email: String, passwd: String) {
            currentUser = User(userId = userId, email = email, passwd = passwd)
            userState = true
        }
    }
}