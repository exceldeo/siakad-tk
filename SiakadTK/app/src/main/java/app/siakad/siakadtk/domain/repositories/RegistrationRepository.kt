package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.ModelContainer
import app.siakad.siakadtk.domain.ModelState
import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class RegistrationRepository() {
    private var daftarUlangList = MutableLiveData<ModelContainer<ArrayList<DaftarUlangModel>>>()
    private val insertState = MutableLiveData<ModelContainer<String>>()
    private val registrationDB = FirebaseRef(MainRepository.DAFTAR_ULANG_REF).getRef()

    fun initEventListener() {
        registrationDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<DaftarUlangModel>()

                for (dataSS in snapshot.children) {
                    val data: DaftarUlangModel? = dataSS.getValue(DaftarUlangModel::class.java)
                    dataRef.add(data!!)
                }

                daftarUlangList.postValue(
                    ModelContainer(
                        status = ModelState.SUCCESS,
                        data = dataRef
                    )
                )
            }
        })
    }

    fun insertData(data: DaftarUlang) {
        val newKey = registrationDB.push().key.toString()
        val newData = DaftarUlangModel(
            dafulId = newKey,
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            tanggal = data.tanggalLahir,
            fotoBayar = data.fotoBayar
        )

        registrationDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }

}