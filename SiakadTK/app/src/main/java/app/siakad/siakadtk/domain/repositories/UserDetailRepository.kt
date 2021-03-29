package app.siakad.siakadtk.domain.repositories

import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class UserDetailRepository() {
    private val userDetailDB = FirebaseRef(
        FirebaseRef.USER_DETAIL_REF
    ).getRef()

    fun initGetUserDetailListener(listener: UserDetailListener) {
        userDetailDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: DetailPenggunaModel? = snapshot.getValue(DetailPenggunaModel::class.java)

                    if (data != null) {
                        data.userDetailId = snapshot.key.toString()

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

    fun insertData(listener: UserDetailListener, detail: DetailPenggunaModel) {
        val newData = DetailPenggunaModel(
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            fotoBayarAwal = detail.fotoBayarAwal,
            jenisKelamin = detail.jenisKelamin,
            dafulState = detail.dafulState,
            fotoSiswa = detail.fotoSiswa,
            kelas = detail.kelas,
            namaOrtu = detail.namaOrtu,
            tahunAjaran = detail.tahunAjaran,
            tanggalLahir = detail.tanggalLahir,
            userState = detail.userState
        )

        userDetailDB.child(detail.userDetailId).setValue(newData).addOnSuccessListener {
            listener.notifyUserDetailChange(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyUserDetailChange(ModelContainer.getFailModel())
        }
    }
}