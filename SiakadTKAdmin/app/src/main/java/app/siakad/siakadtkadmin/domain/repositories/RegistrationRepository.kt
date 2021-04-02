package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RegistrationRepository() {
    private var registrationList = MutableLiveData<ModelContainer<ArrayList<DaftarUlangModel>>>()

    private val registrationDB = FirebaseRef(
        FirebaseRef.DAFTAR_ULANG_REF
    ).getRef()

    fun initGetRegistrationListListener(listener: RegistrationListListener, verified: Boolean = true) {
        registrationDB.orderByChild("status").equalTo(verified)
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val dataRef = arrayListOf<DaftarUlangModel>()

                    for (dataSS in snapshot.children) {
                        val data: DaftarUlangModel? = dataSS.getValue(DaftarUlangModel::class.java)
                        data?.dafulId = dataSS.key.toString()
                        dataRef.add(data!!)

                        listener.setRegistrationList(
                            ModelContainer(
                                status = ModelState.SUCCESS,
                                data = dataRef
                            )
                        )
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}
        })
    }
}