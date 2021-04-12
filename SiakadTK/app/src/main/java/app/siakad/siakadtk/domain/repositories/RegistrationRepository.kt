package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class RegistrationRepository() {
    private var registrationList = MutableLiveData<ModelContainer<ArrayList<DaftarUlangModel>>>()
    private val insertState = MutableLiveData<ModelContainer<String>>()
    private val registrationDB = FirebaseRef(FirebaseRef.DAFTAR_ULANG_REF).getRef()

    fun initEventListener() {
        registrationDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<DaftarUlangModel>()

                for (dataSS in snapshot.children) {
                    val data: DaftarUlangModel? = dataSS.getValue(DaftarUlangModel::class.java)
                    data?.dafulId = dataSS.key.toString()
                    dataRef.add(data!!)
                }

                registrationList.postValue(
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
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val newData = DaftarUlangModel(
            dafulId = newKey,
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            tanggal = todayDate,
            thnAjaran = data.thnAjaran,
            fotoBayar = data.fotoBayar
        )

        registrationDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun updateData(data: DaftarUlang) {
        val currentKey = registrationDB.key.toString()
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val updateData = DaftarUlangModel(
            dafulId = currentKey,
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            tanggal = todayDate,
            fotoBayar = data.fotoBayar
        )

        registrationDB.child(currentKey).setValue(updateData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Sukses"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }

}