package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.KeranjangModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.infrastructure.data.Keranjang
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BasketRepository() {
    private var basketList = MutableLiveData<ModelContainer<ArrayList<KeranjangModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val basketDB = FirebaseRef(FirebaseRef.KERANJANG_REF).getRef()

    fun initEventListener() {
        basketDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<KeranjangModel>()

                for (dataSS in snapshot.children) {
                    val data: KeranjangModel? = dataSS.getValue(
                        KeranjangModel::class.java)
                    data?.keranjangId = dataSS.key.toString()
                    dataRef.add(data!!)
                }

                basketList.postValue(
                    ModelContainer(
                        status = ModelState.SUCCESS,
                        data = dataRef
                    )
                )
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addItem(data: DetailKeranjangModel) {

    }

    fun insertDataKeranjang(data: Keranjang, detail: ArrayList<DetailKeranjangModel>) {
        val newKey = basketDB.push().key.toString()
        val newData = KeranjangModel(
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            keranjangId = newKey,
            detailKeranjang = detail
        )

        basketDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun getList(): LiveData<ModelContainer<ArrayList<KeranjangModel>>> {
        return basketList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}