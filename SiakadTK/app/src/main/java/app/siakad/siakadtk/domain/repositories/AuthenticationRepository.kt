package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.listeners.register.RegisterListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository {
    private val authState = MutableLiveData<ModelContainer<String>>()

    companion object {
        val fbAuth = FirebaseAuth.getInstance()
        lateinit var currentPengguna: PenggunaModel
        var userState: Boolean = false

        fun setUser(userId: String, email: String, passwd: String) {
            currentPengguna = PenggunaModel(
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

    fun register(listener: RegisterListener, email: String, passwd: String) {
        fbAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                listener.notifyRegisterStatus(ModelContainer.getSuccesModel("Berhasil daftar!"))
            } else {
                listener.notifyRegisterStatus(ModelContainer.getFailModel())
            }
        }
//        sendEmailVerification()
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
