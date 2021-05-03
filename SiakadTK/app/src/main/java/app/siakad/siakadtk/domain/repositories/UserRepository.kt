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
import app.siakad.siakadtk.domain.utils.listeners.registration.RegistrationListener
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import app.siakad.siakadtk.infrastructure.data.DetailPengguna
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class UserRepository() {
    private var userState = MutableLiveData<ModelContainer<PenggunaModel>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val userDB = FirebaseRef(FirebaseRef.USER_REF).getRef()
    private var detailPengguna = DetailPenggunaModel()
    private var thisPengguna = PenggunaModel()
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
        thisPengguna = PenggunaModel(
            userId = newKey,
            email = pengguna.email,
            nama = pengguna.nama,
            passwd = pengguna.passwd,
            role = UserRoleModel.SISWA.str,
            detailPengguna = pengguna.detail,
            status = false
        )
        val newData = thisPengguna

        userDB.child(newKey).setValue(newData).addOnSuccessListener {
            listener.notifyDataInsertStatus(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            listener.notifyDataInsertStatus(ModelContainer.getFailModel())
        }
    }

    fun updateDetailData(listener: RegistrationListener, detail: DaftarUlang) {
        detailPengguna = DetailPenggunaModel(
            kelas = detail.kelas,
            jenisKelamin = detail.jenisKelamin,
            tanggalLahir = detail.tanggalLahir,
            namaOrtu = detail.namaWali,
            tahunAjaran = detail.tahunAjaran,
            fotoBayarAwal = detail.fotoBayar,
        )
        updateDataFromRegistration(listener, detail)
    }

    fun updateDataFromRegistration(listener: RegistrationListener, daful: DaftarUlang) {
        val currentKey = userDB.key.toString()
        val updateData = PenggunaModel(
            userId = currentKey,
            email = thisPengguna.email,
            nama = thisPengguna.nama,
            passwd = thisPengguna.passwd,
            role = UserRoleModel.SISWA.str,
            noHP = daful.noHP,
            alamat = daful.alamat,
            detailPengguna = detailPengguna
        )
        val newData = updateData.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${FirebaseRef.USER_REF}/${updateData.userId}" to newData
        )

        FirebaseDatabase.getInstance().reference.updateChildren(childUpdates).addOnSuccessListener {
            listener.notifyUserDetailChangeStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyUserDetailChangeStatus(ModelContainer.getFailModel())
        }
    }

    fun getUser(): LiveData<ModelContainer<PenggunaModel>> {
        return userState
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}