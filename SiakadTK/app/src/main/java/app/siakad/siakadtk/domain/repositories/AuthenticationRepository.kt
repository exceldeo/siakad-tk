package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.listeners.login.LoginListener
import app.siakad.siakadtk.domain.utils.listeners.register.RegisterListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository {
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

    fun login(listener: LoginListener, email: String, passwd: String) {
        fbAuth.signInWithEmailAndPassword(email, passwd).addOnSuccessListener {
            listener.notifyLoginStatus(ModelContainer.getSuccesModel("Berhasil masuk!"))
        }.addOnFailureListener { e -> listener.notifyLoginStatus(ModelContainer.getFailModel()) }
    }

    fun register(listener: RegisterListener, email: String, passwd: String) {
        fbAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                listener.notifyRegisterStatus(ModelContainer.getSuccesModel("Berhasil daftar!"))
            } else {
                listener.notifyRegisterStatus(ModelContainer.getFailModel())
            }
        }
    }

    fun logout() {
        fbAuth.signOut()
    }
}
