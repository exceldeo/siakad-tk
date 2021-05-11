package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RegistrationRepository() {

  private val registrationDB = FirebaseRef(
    FirebaseRef.DAFTAR_ULANG_REF
  ).getRef()

  fun initGetRegistrationListListener(
    listener: RegistrationListListener,
    verified: Boolean = true
  ) {
    registrationDB.orderByChild("statusDaful").equalTo(verified)
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
          val data: DaftarUlangModel? = snapshot.getValue(DaftarUlangModel::class.java)
          if (data != null) {
            data?.dafulId = snapshot.key.toString()

            listener.addRegistrationItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data!!
              )
            )
          }
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val data: DaftarUlangModel? = snapshot.getValue(DaftarUlangModel::class.java)
          if (data != null) {
            data?.dafulId = snapshot.key.toString()

            listener.addRegistrationItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data!!
              )
            )
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
          val data: DaftarUlangModel? = snapshot.getValue(DaftarUlangModel::class.java)
          if (data != null) {
            data?.dafulId = snapshot.key.toString()

            listener.addRegistrationItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data!!
              )
            )
          }
        }
      })
  }

  fun updateRegisData(listener: RegistrationDetailListener, data: DaftarUlangModel) {
    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.dafulId}" to newData
    )

    registrationDB.updateChildren(childUpdates).addOnSuccessListener {
      listener.notifyRegistrationDetailChangeStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyRegistrationDetailChangeStatus(ModelContainer.getFailModel())
    }
  }
}