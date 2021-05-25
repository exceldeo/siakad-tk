package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderDetailListener
import app.siakad.siakadtkadmin.presentation.screens.announcement.AnnouncementListFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class AnnouncementRepository {
  private val eventListeners: ArrayList<Any> = arrayListOf()

  private val announcementDB = FirebaseRef(
    FirebaseRef.PENGUMUMAN_REF
  ).getRef()

  fun initGetAnnouncementListListener(
    listener: AnnouncementListListener,
    type: String = AnnouncementListFragment.TO_ALL
  ) {
    val eventListener = announcementDB.orderByChild("tipe").equalTo(type)
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

    eventListeners.add(eventListener)
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

  fun removeAnnouncementData(listener: AnnouncementAddListener, data: PengumumanModel) {
    announcementDB.child(data.pengumumanId).removeValue().addOnSuccessListener {
      listener.notifyAnnouncementDeleteStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener { listener.notifyAnnouncementDeleteStatus(ModelContainer.getFailModel()) }
  }

  fun removeEventListener() {
    eventListeners.forEach {
      if (it is ChildEventListener) {
        announcementDB.removeEventListener(it)
      } else if (it is ValueEventListener) {
        announcementDB.removeEventListener(it)
      }
    }
  }
}