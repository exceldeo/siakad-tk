package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.main.MainListener
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import app.siakad.siakadtkadmin.presentation.screens.classroom.ClassroomListFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ClassroomRepository {
  private val eventListeners: ArrayList<Any> = arrayListOf()

  private val classroomDB = FirebaseRef(
    FirebaseRef.KELAS_REF
  ).getRef()

  fun initGetClassroomListListener(
    listener: ClassroomListListener,
    type: String = ClassroomListFragment.TK_A
  ) {
    val eventListener = classroomDB.orderByChild("namaKelas").startAt(type).endAt(type + "\uf8ff")
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val dataRef = arrayListOf<KelasModel>()

          forloop@ for (dataSS in snapshot.children) {
            when (dataSS.value) {
              is String -> {
                val data: KelasModel? =
                  snapshot.getValue(KelasModel::class.java)
                if (data != null) {
                  data.kelasId = snapshot.key.toString()
                  dataRef.add(data)
                }

                listener.setClassroomList(
                  ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                  )
                )
                break@forloop
              }
              is KelasModel -> {
                val data: KelasModel? =
                  dataSS.getValue(KelasModel::class.java)
                if (data != null) {
                  data.kelasId = dataSS.key.toString()
                  dataRef.add(data)
                }

                listener.setClassroomList(
                  ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                  )
                )
              }
            }
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {}
      })

    eventListeners.add(eventListener)
  }

  fun initGetClassroomListByYearListener(
    listener: ClassroomListListener,
    year: Int,
  ) {
    val eventListener = classroomDB.orderByChild("tahunSelesai").equalTo(year.toDouble())
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val dataRef = arrayListOf<KelasModel>()

          forloop@ for (dataSS in snapshot.children) {
            when (dataSS.value) {
              is String -> {
                val data: KelasModel? =
                  snapshot.getValue(KelasModel::class.java)
                if (data != null) {
                  data.kelasId = snapshot.key.toString()
                  dataRef.add(data)
                }

                listener.setClassroomList(
                  ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                  )
                )
                break@forloop
              }
              is KelasModel -> {
                val data: KelasModel? =
                  dataSS.getValue(KelasModel::class.java)
                if (data != null) {
                  data.kelasId = dataSS.key.toString()
                  dataRef.add(data)
                }

                listener.setClassroomList(
                  ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                  )
                )
              }
            }
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {}
      })

    eventListeners.add(eventListener)
  }

  fun getClassById(listener: Any, id: String) {
    val eventListener =
      classroomDB.orderByKey().equalTo(id).addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val kelas = snapshot.getValue(KelasModel::class.java)

          if (kelas != null) {
            kelas.kelasId = snapshot.key.toString()
            if (listener is AnnouncementAddListener) {
              listener.setClassById(ModelContainer.getSuccesModel(kelas))
            } else if (listener is RegistrationListListener) {
              listener.setClass(ModelContainer.getSuccesModel(kelas))
            }
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {}
      })

    eventListeners.add(eventListener)
  }

  fun insertData(listener: ClassroomAddListener, data: KelasModel) {
    val newKey = classroomDB.push().key.toString()
    data.kelasId = newKey

    classroomDB.child(newKey).setValue(data).addOnSuccessListener {
      listener.notifyClassroomAddStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyClassroomAddStatus(ModelContainer.getFailModel())
    }
  }

  fun updateClassroomUser(listener: RegistrationDetailListener, data: KelasModel, userId: String) {
    data.daftarSiswa.add(userId)

    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.kelasId}" to newData
    )

    classroomDB.updateChildren(childUpdates).addOnSuccessListener {
      listener.notifyClassroomUserChangeStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyClassroomUserChangeStatus(ModelContainer.getFailModel())
    }
  }

  fun removeEventListener() {
    eventListeners.forEach {
      if (it is ChildEventListener) {
        classroomDB.removeEventListener(it)
      } else if (it is ValueEventListener) {
        classroomDB.removeEventListener(it)
      }
    }
  }
}