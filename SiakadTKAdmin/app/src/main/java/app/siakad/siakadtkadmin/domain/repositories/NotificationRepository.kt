package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.NotifikasiModel
import app.siakad.siakadtkadmin.infrastructure.data.Notifikasi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class NotificationRepository() {
    private var notificationList = MutableLiveData<ModelContainer<ArrayList<NotifikasiModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val notificationDB = FirebaseRef(MainRepository.NOTIFIKASI_REF).getRef()

    fun initEventListener() {
        notificationDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<NotifikasiModel>()

                for (dataSS in snapshot.children) {
                    val data: NotifikasiModel? = dataSS.getValue(NotifikasiModel::class.java)
                    dataRef.add(data!!)
                }

                notificationList.postValue(
                    ModelContainer(
                        status = ModelState.SUCCESS,
                        data = dataRef
                    )
                )
            }
        })
    }

    fun insertData(data: Notifikasi) {
        val newKey = notificationDB.push().key.toString()
        val newData = NotifikasiModel(
            notifikasiId = newKey,
            adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            judul = data.judul,
            keterangan = data.keterangan,
            tanggal = data.tanggal
        )

        notificationDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getNotificationList(): LiveData<ModelContainer<ArrayList<NotifikasiModel>>> {
        return notificationList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}