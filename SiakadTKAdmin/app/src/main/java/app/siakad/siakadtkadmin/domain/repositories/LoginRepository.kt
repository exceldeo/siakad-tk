package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.models.UserModel
import app.siakad.siakadtkadmin.domain.models.UserRoleModel
import app.siakad.siakadtkadmin.infrastructure.DataContainer
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import java.lang.Exception

class LoginRepository {
    private val loginState = MutableLiveData<DataContainer<String>>()

    fun login(email: String, passwd: String) {
        val auth = AuthenticationRepository.fbAuth

        UserRepository.userDB.orderByChild("email").equalTo(email).get().addOnSuccessListener {
            val user = it.getValue(UserModel::class.java)

            if (user != null) {
                if (user.role.equals(UserRoleModel.ADMIN.str)) {
                    auth.signInWithEmailAndPassword(email, passwd).addOnSuccessListener(
                        object : OnSuccessListener<AuthResult> {
                            override fun onSuccess(auth: AuthResult?) {
                                loginState.postValue(DataContainer.getSuccesModel("Berhasil masuk!"))

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
                            loginState.postValue(DataContainer.getFailModel(e.message.toString()))
                        }
                    })
                } else {
                    loginState.postValue(DataContainer.getFailModel("Akun ini nukan Akun Admin"))
                }
            }
        }.addOnFailureListener { exception: Exception ->
            loginState.postValue(DataContainer.getFailModel("Gagal mendapatkan Akun"))
        }
    }

    fun getloginState() : LiveData<DataContainer<String>> {
        return loginState
    }
}