package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementListListener
import app.siakad.siakadtkadmin.presentation.screens.announcement.AnnouncementListFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class AnnouncementRepository {
  private val announcementDB = FirebaseRef(
    FirebaseRef.PENGUMUMAN_REF
  ).getRef()

  fun initGetAnnouncementListListener(
    listener: AnnouncementListListener,
    type: String = AnnouncementListFragment.TO_ALL
  ) {
    announcementDB.orderByChild("tipe").equalTo(type)
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
          val data: PengumumanModel? =
            snapshot.getValue(PengumumanModel::class.java)

          if (data != null) {
            data.pengumumanId = snapshot.key.toString()
            listener.updateAnnouncementItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val data: PengumumanModel? =
            snapshot.getValue(PengumumanModel::class.java)

          if (data != null) {
            data.pengumumanId = snapshot.key.toString()
            listener.addAnnouncementItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
          val data: PengumumanModel? =
            snapshot.getValue(PengumumanModel::class.java)

          if (data != null) {
            data.pengumumanId = snapshot.key.toString()
            listener.removeAnnouncementItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }
      })
  }

  fun insertData(listener: AnnouncementAddListener, data: PengumumanModel) {
    val newKey = announcementDB.push().key.toString()
    data.pengumumanId = newKey
    data.adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!

    announcementDB.child(newKey).setValue(data).addOnSuccessListener {
      listener.notifyAnnouncementAddStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyAnnouncementAddStatus(ModelContainer.getFailModel())
    }
  }

  fun updateData(listener: AnnouncementAddListener, data: PengumumanModel) {
    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.pengumumanId}" to newData
    )

    announcementDB.updateChildren(childUpdates).addOnSuccessListener {
      listener.notifyAnnouncementUpdateStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyAnnouncementUpdateStatus(ModelContainer.getFailModel())
    }
  }
}