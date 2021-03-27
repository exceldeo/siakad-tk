package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AnnouncementRepository() {
    private var announcementList = MutableLiveData<ModelContainer<ArrayList<PengumumanModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val announcementDB = FirebaseRef(
        FirebaseRef.PENGUMUMAN_REF
    ).getRef()

    fun initEventListener() {
        announcementDB.addValueEventListener(object : ValueEventListener{
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

    fun insertData(data: Pengumuman) {
        val newKey = announcementDB.push().key.toString()
        val newData = PengumumanModel(
            pengumumanId = newKey,
            adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            judul = data.judul,
            keterangan = data.keterangan,
            tanggal = data.tanggal
        )

        announcementDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getAnnouncementList(): LiveData<ModelContainer<ArrayList<PengumumanModel>>> {
        return announcementList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}