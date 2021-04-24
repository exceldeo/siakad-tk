package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomListListener
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import app.siakad.siakadtkadmin.presentation.screens.classroom.ClassroomListFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class ClassroomRepository {
    private val classroomDB = FirebaseRef(
        FirebaseRef.KELAS_REF
    ).getRef()

    fun initGetClassroomListListener(
        listener: ClassroomListListener,
        type: String = ClassroomListFragment.TK_A
    ) {
        classroomDB.orderByChild("namaKelas").equalTo(type)
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
}