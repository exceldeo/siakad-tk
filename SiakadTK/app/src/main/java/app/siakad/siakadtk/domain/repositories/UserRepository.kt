package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtk.domain.utils.listeners.register.RegisterListener
import app.siakad.siakadtk.domain.utils.listeners.user.UserListListener
import app.siakad.siakadtk.infrastructure.data.DetailPengguna
import app.siakad.siakadtk.infrastructure.data.Pengguna
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class UserRepository() {
    private var userState = MutableLiveData<ModelContainer<PenggunaModel>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val userDB = FirebaseRef(FirebaseRef.USER_REF).getRef()
    private var detailPengguna = DetailPenggunaModel()

    fun initGetUserListListener(listener: UserListListener, verified: Boolean = true) {
        userDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val dataRef = arrayListOf<PenggunaModel>()

                    forloop@ for (dataSS in snapshot.children) {
                        when (dataSS.value) {
                            is String -> {
                                val data: PenggunaModel? = snapshot.getValue(PenggunaModel::class.java)
                                if (data != null) {
                                    if (data.status == verified) {
                                        data.userId = snapshot.key.toString()
                                        dataRef.add(data)

                                        listener.setUserList(
                                            ModelContainer(
                                                status = ModelState.SUCCESS,
                                                data = dataRef
                                            )
                                        )
                                        break@forloop
                                    }
                                }
                            }
                            is PenggunaModel -> {
                                val data: PenggunaModel? = dataSS.getValue(PenggunaModel::class.java)
                                if (data != null) {
                                    if (data.status == verified) {
                                        data.userId = dataSS.key.toString()
                                        dataRef.add(data)

                                        listener.setUserList(
                                            ModelContainer(
                                                status = ModelState.SUCCESS,
                                                data = dataRef
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}
            })
    }

    fun getUserByEmail(email: String) {
        userDB.orderByChild("email").equalTo(email).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(PenggunaModel::class.java)

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

    fun insertData(listener: RegisterListener, pengguna: Pengguna) {
        val newKey = userDB.push().key.toString()
        val newData = PenggunaModel(
            userId = newKey,
            email = pengguna.email,
            nama = pengguna.nama,
            passwd = pengguna.passwd,
            role = UserRoleModel.SISWA.str,
            detailPengguna = pengguna.detail
        )

        userDB.child(newKey).setValue(newData).addOnSuccessListener {
            listener.notifyDataInsertStatus(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            listener.notifyDataInsertStatus(ModelContainer.getFailModel())
        }
    }

    fun updateDetailData(detail: DetailPengguna) {
        detailPengguna = DetailPenggunaModel(
            kelas = detail.kelas,
            tahunAjaran = detail.thnAjaran,
            jenisKelamin = detail.jenisKelamin,
            tanggalLahir = detail.tanggalLahir,
            namaOrtu = detail.namaOrtu,
            userState = detail.userState,
            dafulState = detail.dafulState
        )
    }

    fun updateData(pengguna: Pengguna) {
        val currentKey = userDB.key.toString()
        val updateData = PenggunaModel(
            userId = currentKey,
            email = pengguna.email,
            nama = pengguna.nama,
            passwd = pengguna.passwd,
            role = UserRoleModel.SISWA.str,
            detailPengguna = detailPengguna
        )
        userDB.child(currentKey).setValue(updateData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getUser(): LiveData<ModelContainer<PenggunaModel>> {
        return userState
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}