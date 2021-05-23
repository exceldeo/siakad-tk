package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.infrastructure.data.DetailPengguna
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class UserDetailRepository() {
    private val userDetailDB = FirebaseRef(FirebaseRef.USER_DETAIL_REF).getRef()
    private val insertState = MutableLiveData<ModelContainer<String>>()

    fun initGetUserDetailListener(listener: UserDetailListener) {
        userDetailDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: DetailPenggunaModel? = snapshot.getValue(DetailPenggunaModel::class.java)

                    if (data != null) {
//                        data.userDetailId = snapshot.key.toString()

                        listener.setUserDetail(
                            ModelContainer(
                                status = ModelState.SUCCESS,
                                data = data
                            )
                        )
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}
            })
    }

    fun insertData(detail: DetailPengguna) {
        val currentKey = userDetailDB.push().key.toString()
        val newData = DetailPenggunaModel(
            kelasId = detail.kelas,
            jenisKelamin = detail.jenisKelamin,
            tanggalLahir = detail.tanggalLahir,
            namaOrtu = detail.namaOrtu
        )

        userDetailDB.child(currentKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}