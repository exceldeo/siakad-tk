package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.registration.UserListener
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.text.SimpleDateFormat
import java.util.*

class RegistrationRepository() {
    private val registrationDB = FirebaseRef(FirebaseRef.DAFTAR_ULANG_REF).getRef()

    fun initEventListener(listener: UserListener) {
        registrationDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: DaftarUlangModel? = snapshot.getValue(DaftarUlangModel::class.java)
                    data?.dafulId = snapshot.key.toString()

                    listener.addDataDafulUser(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = data
                        )
                    )
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: DaftarUlangModel? = snapshot.getValue(DaftarUlangModel::class.java)
                    data?.dafulId = snapshot.key.toString()

                    listener.changeDataDafulUser(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = data
                        )
                    )
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val data: DaftarUlangModel? = snapshot.getValue(DaftarUlangModel::class.java)
                    data?.dafulId = snapshot.key.toString()

                    listener.removeDataDafulUser(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = data
                        )
                    )
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun insertData(listener: UserListener, data: DaftarUlang) {
        val newKey = registrationDB.push().key.toString()
        val todayDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val newData = DaftarUlangModel(
            dafulId = newKey,
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            tanggal = todayDate,
            fotoBayar = data.fotoBayar,
            statusDaful = data.statusDaful
        )

        registrationDB.child(newKey).setValue(newData).addOnSuccessListener {
            listener.notifyUserDetailChangeStatus(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            listener.notifyUserDetailChangeStatus(ModelContainer.getFailModel())
        }
    }

    fun updateData(listener: UserListener, data: DaftarUlang) {
        val currentKey = registrationDB.key.toString()
        val todayDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val updateData = DaftarUlangModel(
            dafulId = currentKey,
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            tanggal = todayDate,
            fotoBayar = data.fotoBayar,
            statusDaful = data.statusDaful
        )

        registrationDB.child(currentKey).setValue(updateData).addOnSuccessListener {
            listener.notifyUserDetailChangeStatus(ModelContainer.getSuccesModel("Sukses Update Daftar Ulang"))
        }.addOnFailureListener {
            listener.notifyUserDetailChangeStatus(ModelContainer.getFailModel())
        }
    }

}