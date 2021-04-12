package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.models.DetailPenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class UserDetailRepository() {
    private val userDetailDB = FirebaseRef(
        FirebaseRef.USER_DETAIL_REF
    ).getRef()

    fun initGetUserDetailListener(listener: UserDetailListener, userId: String) {
        userDetailDB.orderByChild("userId").equalTo(userId)
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

    fun updateData(listener: UserDetailListener, detail: DetailPenggunaModel) {
        val newData = DetailPenggunaModel(
            userId = detail.userId,
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