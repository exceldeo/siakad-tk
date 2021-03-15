package app.siakad.siakadtk.domain.repositories

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.ModelContainer
import app.siakad.siakadtk.domain.models.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository {
    private val authState = MutableLiveData<ModelContainer<String>>()

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
    }

    fun login(email: String, passwd: String) {
        fbAuth.signInWithEmailAndPassword(email, passwd).addOnSuccessListener {
            authState.postValue(ModelContainer.getSuccesModel("Berhasil masuk!"))
        }.addOnFailureListener { e -> authState.postValue(ModelContainer.getFailModel()) }
    }

    fun register(email: String, passwd: String) {
        fbAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                authState.postValue(ModelContainer.getSuccesModel("Berhasil daftar!"))
            } else {
                authState.postValue(ModelContainer.getFailModel())
            }
        }
        sendEmailVerification()
    }

    fun logout() {
        fbAuth.signOut()
    }

    private fun sendEmailVerification() {
        fbAuth.currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                authState.postValue(ModelContainer.getSuccesModel("Verification email sent to ${fbAuth.currentUser!!.email} "))
            } else {
                authState.postValue(ModelContainer.getFailModel())
            }
        }
    }

    fun isEmailVerified(): Boolean {
        return fbAuth.currentUser!!.isEmailVerified
    }

    fun getAuthState() : LiveData<ModelContainer<String>> {
        return authState
    }
}
