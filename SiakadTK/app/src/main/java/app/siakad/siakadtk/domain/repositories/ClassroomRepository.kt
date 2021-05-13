package app.siakad.siakadtk.domain.repositories

import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.KelasModel
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.helpers.model.ClassTypeModel
import app.siakad.siakadtk.domain.utils.listeners.classroom.ClassroomListListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ClassroomRepository {
    private val classroomDB = FirebaseRef(FirebaseRef.KELAS_REF).getRef()

    fun initGetClassroomListListenerByType (listener: ClassroomListListener, type: String = ClassTypeModel.TK_A.str) {
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

    fun initGetClassroomList(listener: ClassroomListListener) {
        classroomDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<KelasModel>()

                for (dataSS in snapshot.children) {
                    val data: KelasModel? =
                        dataSS.getValue(KelasModel::class.java)
                    if (data != null) {
                        data.kelasId = dataSS.key.toString()
                        dataRef.add(data)
                    }
                }

                listener.setClassroomList(
                    ModelContainer(
                        status = ModelState.SUCCESS,
                        data = dataRef
                    )
                )
            }
        })
    }
}