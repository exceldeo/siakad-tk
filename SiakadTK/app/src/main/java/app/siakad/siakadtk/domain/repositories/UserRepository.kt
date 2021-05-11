package app.siakad.siakadtk.domain.repositories

import android.util.Log
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
import app.siakad.siakadtk.infrastructure.data.Pengguna
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

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

    fun getUserById(listener: RegistrationListener) {
        userDB.orderByKey().equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(PenggunaModel::class.java)

                if (user != null) {
                    listener.addDataUser(ModelContainer.getSuccesModel(user))
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(PenggunaModel::class.java)

                if (user != null) {
                    listener.addDataUser(ModelContainer.getSuccesModel(user))
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {

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
        val newKey = AuthenticationRepository.fbAuth.currentUser?.uid!!
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
            listener.notifyDataInsertStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyDataInsertStatus(ModelContainer.getFailModel())
        }
    }

    fun updateDetailData(listener: RegistrationListener, detail: DaftarUlang, dataUser: Pengguna) {
        detailPengguna = DetailPenggunaModel(
            kelas = detail.kelas,
            jenisKelamin = detail.jenisKelamin,
            tanggalLahir = detail.tanggalLahir,
            namaOrtu = detail.namaWali,
            tahunAjaran = detail.tahunAjaran,
            fotoBayarAwal = detail.fotoBayar,
        )
        updateDataFromRegistration(listener, detail, dataUser)
    }

    private fun updateDataFromRegistration(
        listener: RegistrationListener,
        daful: DaftarUlang,
        dataUser: Pengguna
    ) {
        val currentKey = AuthenticationRepository.fbAuth.currentUser?.uid!!
        val updateData = PenggunaModel(
            userId = currentKey,
            email = dataUser.email,
            nama = daful.namaSiswa,
            passwd = dataUser.passwd,
            role = UserRoleModel.SISWA.str,
            noHP = daful.noHP,
            alamat = daful.alamat,
            detailPengguna = detailPengguna
        )
        Log.i("UPDATE DATA", currentKey)
        val newData = updateData.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${FirebaseRef.USER_REF}/${AuthenticationRepository.fbAuth.currentUser?.uid!!}" to newData
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