package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.ModelContainer
import app.siakad.siakadtk.domain.ModelState
import app.siakad.siakadtk.domain.models.UserModel
import app.siakad.siakadtk.domain.models.UserRoleModel
import app.siakad.siakadtk.infrastructure.data.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class UserRepository() {
    private var userState = MutableLiveData<ModelContainer<UserModel>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val userDB = FirebaseRef(MainRepository.USER_REF).getRef()

    fun getUserByEmail(email: String) {
        userDB.orderByChild("email").equalTo(email).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(UserModel::class.java)

                if (user != null) {
                    userState.postValue(ModelContainer.getSuccesModel(user))
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertData(user: User) {
        val newKey = userDB.push().key.toString()
        val newData = UserModel(
            userId = newKey,
            alamat = user.alamat,
            email = user.email,
            nama = user.nama,
            noHP = user.noHP,
            passwd = user.passwd,
            role = UserRoleModel.SISWA.str
        )

        userDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun updateData(user: User) {
        val currentKey = userDB.key.toString()
        val updateData = UserModel(
            userId = currentKey,
            alamat = user.alamat,
            email = user.email,
            nama = user.nama,
            noHP = user.noHP,
            passwd = user.passwd,
            role = UserRoleModel.SISWA.str
        )
        userDB.child(currentKey).setValue(updateData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getUser(): LiveData<ModelContainer<UserModel>> {
        return userState
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}