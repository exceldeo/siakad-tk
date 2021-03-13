package app.siakad.siakadtk.domain.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.domain.ModelContainer
import app.siakad.siakadtk.domain.ModelState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AnnouncementRepository() {
    private var announcementList = MutableLiveData<ModelContainer<ArrayList<PengumumanModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()
    private val announcementDB = FirebaseRef(MainRepository.PENGUMUMAN_REF).getRef()

    fun initEventListener() {
        announcementDB.getRef().addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<PengumumanModel>()

                for (dataSS in snapshot.children) {
                    val data: PengumumanModel? = dataSS.getValue(PengumumanModel::class.java)
                    dataRef.add(data!!)
                }

                announcementList.postValue(ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                ))
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
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
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