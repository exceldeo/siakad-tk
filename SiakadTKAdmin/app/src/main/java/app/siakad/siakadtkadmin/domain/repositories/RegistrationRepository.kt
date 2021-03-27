package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.ModelState
import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RegistrationRepository() {
    private var registrationList = MutableLiveData<ModelContainer<ArrayList<DaftarUlangModel>>>()

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

                registrationList.postValue(
                    ModelContainer(
                    status = ModelState.SUCCESS,
                    data = dataRef
                )
                )
            }
        })
    }

    fun getRegistrationList(): LiveData<ModelContainer<ArrayList<DaftarUlangModel>>> {
        return registrationList
    }
}