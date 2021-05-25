package app.siakad.siakadtk.domain.repositories

import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.KelasModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.helpers.model.ClassTypeModel
import app.siakad.siakadtk.domain.utils.listeners.classroom.ClassroomListListener
import app.siakad.siakadtk.domain.utils.listeners.classroom.ClassroomListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ClassroomRepository {
    private val classroomDB = FirebaseRef(FirebaseRef.KELAS_REF).getRef()

    fun initGetClassroomListListenerById (listener: ClassroomListener, id: String) {
        classroomDB.orderByKey().equalTo(id)
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val data = snapshot.getValue(KelasModel::class.java)

                    if (data != null) {
                        data.kelasId = snapshot.key.toString()
                        listener.setClassroomById(ModelContainer.getSuccesModel(data))
                    }
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data = snapshot.getValue(KelasModel::class.java)

                    if (data != null) {
                        data.kelasId = snapshot.key.toString()
                        listener.setClassroomById(ModelContainer.getSuccesModel(data))
                    }
                }
                override fun onChildRemoved(snapshot: DataSnapshot) {}
            })
    }

    fun initGetClassroomListByYearListener(
        listener: ClassroomListListener,
        year: Int,
    ) {
        classroomDB.orderByChild("tahunSelesai").equalTo(year.toDouble())
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
}