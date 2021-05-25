package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.utils.listeners.announcement.AnnouncementServiceListener
import app.siakad.siakadtk.domain.utils.helpers.model.AnnouncementTypeModel
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementListListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class AnnouncementRepository {
    private var announcementList = MutableLiveData<ModelContainer<ArrayList<PengumumanModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()
    private val announcementDB = FirebaseRef(FirebaseRef.PENGUMUMAN_REF).getRef()

    private var eventListeners: ArrayList<Any> = arrayListOf()

    fun initGetAnnouncementListListener(
        listener: AnnouncementListListener
    ) {
        announcementDB.orderByChild("tipe").equalTo(AnnouncementTypeModel.TO_ALL.str)
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

    fun initServiceChildEventListener(listener: AnnouncementServiceListener) {
        val eventListener = announcementDB.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data: PengumumanModel? = snapshot.getValue(PengumumanModel::class.java)
                data?.pengumumanId = snapshot.key.toString()

                listener.sendAnnouncementNotification(ModelContainer.getSuccesModel(data!!))
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })

        eventListeners.add(eventListener)
    }

    fun initGetAnnouncementListListenerByUserId(
        listener: AnnouncementListListener
    ) {
        announcementDB.orderByChild("tujuanId").equalTo(AuthenticationRepository.fbAuth.currentUser!!.uid)
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

    fun initGetAnnouncementListListenerByClass(
        listener: AnnouncementListListener, classId: String
    ) {
        announcementDB.orderByChild("tujuanId").equalTo(classId)
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

    fun removeListener() {
        eventListeners.forEachIndexed { index, any ->
            if (any is ValueEventListener) {
                announcementDB.removeEventListener(any)
            } else if (any is ChildEventListener) {
                announcementDB.removeEventListener(any)
            }
        }
    }
}