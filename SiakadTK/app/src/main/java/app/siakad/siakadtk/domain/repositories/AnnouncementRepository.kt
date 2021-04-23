package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AnnouncementRepository() {
    private var announcementList = MutableLiveData<ModelContainer<ArrayList<PengumumanModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()
    private val announcementDB = FirebaseRef(FirebaseRef.PENGUMUMAN_REF).getRef()

    fun initEventListener() {
        announcementDB.getRef().addValueEventListener(object : ValueEventListener {
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

    fun getAnnouncementList(): LiveData<ModelContainer<ArrayList<PengumumanModel>>> {
        return announcementList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}