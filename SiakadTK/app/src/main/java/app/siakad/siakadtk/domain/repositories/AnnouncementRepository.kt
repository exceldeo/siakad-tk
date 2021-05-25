package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.utils.listeners.announcement.AnnouncementServiceListener
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

    fun initEventListener() {
        announcementDB.ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<PengumumanModel>()

                for (dataSS in snapshot.children) {
                    val data: PengumumanModel? = dataSS.getValue(PengumumanModel::class.java)
                    data?.pengumumanId = dataSS.key.toString()
                    dataRef.add(data!!)
                }

                announcementList.postValue(
                    ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                )
                )
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

    fun getAnnouncementList(): LiveData<ModelContainer<ArrayList<PengumumanModel>>> {
        return announcementList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
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