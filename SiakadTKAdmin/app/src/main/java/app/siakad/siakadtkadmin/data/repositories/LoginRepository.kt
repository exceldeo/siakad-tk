package app.siakad.siakadtkadmin.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.data.db.refs.User
import app.siakad.siakadtkadmin.data.db.refs.UserRole
import app.siakad.siakadtkadmin.domain.DataModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import java.lang.Exception

class LoginRepository {
    private val loginState = MutableLiveData<DataModel<String>>()

    fun login(email: String, passwd: String) {
        val auth = AuthenticationRepository.fbAuth

        UserRepository.userDB.orderByChild("email").equalTo(email).get().addOnSuccessListener {
            val user = it.getValue(User::class.java)

            if (user != null) {
                if (user.role.equals(UserRole.ADMIN.str)) {
                    auth.signInWithEmailAndPassword(email, passwd).addOnSuccessListener(
                        object : OnSuccessListener<AuthResult> {
                            override fun onSuccess(auth: AuthResult?) {
                                loginState.postValue(DataModel.getSuccesModel("Berhasil masuk!"))

                                if (AuthenticationRepository.userState) {
                                    AuthenticationRepository.setUser(
                                        user.userId.toString(),
                                        email,
                                        passwd
                                    )
                                }
                            }
                        }).addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(e: Exception) {
                            loginState.postValue(DataModel.getFailModel(e.message.toString()))
                        }
                    })
                } else {
                    loginState.postValue(DataModel.getFailModel("Akun ini nukan Akun Admin"))
                }
            }
        }.addOnFailureListener { exception: Exception ->
            loginState.postValue(DataModel.getFailModel("Gagal mendapatkan Akun"))
        }
    }

    fun getloginState() : LiveData<DataModel<String>> {
        return loginState
    }
}