package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.ModelState
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.models.UserModel
import app.siakad.siakadtkadmin.domain.models.UserRoleModel
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class UserRepository() {
    private var userState = MutableLiveData<ModelContainer<UserModel>>()
    private var userList = MutableLiveData<ModelContainer<ArrayList<UserModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val userDB = FirebaseRef(MainRepository.USER_REF).getRef()

    fun getUserByEmail(email: String) {
        userDB.orderByChild("email").equalTo(email).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(UserModel::class.java)

                if (user != null) {
                    userState.postValue(ModelContainer.getSuccesModel(user))
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}
        })
    }

    fun getAllUser(name: String = "") {
        userDB.orderByChild("role").equalTo(UserRoleModel.SISWA.str).addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val dataRef = arrayListOf<UserModel>()

                for (dataSS in snapshot.children) {
                    val data: UserModel? = dataSS.getValue(UserModel::class.java)
                    if (name.length > 0) {
                        if (data?.nama!!.contains(name)) {
                            dataRef.add(data)
                        }
                    } else {
                        dataRef.add(data!!)
                    }
                }

                userList.postValue(ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                ))
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}
        })
    }

    fun insertData(user: UserModel) {
        val newKey = userDB.push().key.toString()
        val newData = UserModel(
            userId = newKey,
            alamat = user.alamat,
            email = user.email,
            nama = user.nama,
            noHP = user.noHP,
            passwd = user.passwd,
            role = UserRoleModel.ADMIN.str
        )

        userDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getUser(): LiveData<ModelContainer<UserModel>> {
        return userState
    }

    fun getAllUser(): LiveData<ModelContainer<ArrayList<UserModel>>> {
        return userList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}