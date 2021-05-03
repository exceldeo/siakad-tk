package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.models.KeranjangModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtk.domain.utils.listeners.login.LoginListener
import app.siakad.siakadtk.domain.utils.listeners.register.RegisterListener
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
    private val basketDB = FirebaseRef(FirebaseRef.KERANJANG_REF).getRef()
    private var detailKeranjang = arrayListOf<DetailKeranjangModel>()

    fun makeKeranjang(listener: LoginListener, userId: String) {
        val newKey = basketDB.push().key.toString()
        val newData = KeranjangModel(
            userId = userId,
            keranjangId = newKey,
            detailKeranjang = detailKeranjang
        )

        basketDB.child(userId).setValue(newData).addOnSuccessListener {
            listener.notifyMakeKeranjangStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyMakeKeranjangStatus(ModelContainer.getFailModel())
        }
    }

    fun getUserById(listener: LoginListener) {
        userDB.orderByKey().equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(PenggunaModel::class.java)

                if (user != null) {
                    listener.setUser(ModelContainer.getSuccesModel(user))
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

    fun getUserByEmail(listener: LoginListener, email: String) {
        userDB.orderByChild("email").equalTo(email).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(PenggunaModel::class.java)

                if (user != null) {
                    listener.setUser(ModelContainer.getSuccesModel(user))
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(PenggunaModel::class.java)

                if (user != null) {
                    listener.setUser(ModelContainer.getSuccesModel(user))
                }
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
            detailPengguna = pengguna.detail,
            status = false
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
            tahunAjaran = detail.tahunAjaran,
            jenisKelamin = detail.jenisKelamin,
            tanggalLahir = detail.tanggalLahir,
            namaOrtu = detail.namaOrtu
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