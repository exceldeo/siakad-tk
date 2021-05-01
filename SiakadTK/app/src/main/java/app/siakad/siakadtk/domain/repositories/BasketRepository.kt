package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.KeranjangModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.basket.BasketListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope

class BasketRepository() {
    private var basketList = MutableLiveData<ModelContainer<ArrayList<KeranjangModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val basketDB = FirebaseRef(FirebaseRef.KERANJANG_REF).getRef()
    private var detailKeranjang = arrayListOf<DetailKeranjangModel>()

    fun makeKeranjang() {
        val newKey = basketDB.push().key.toString()
        val newData = KeranjangModel(
            userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            keranjangId = newKey,
            detailKeranjang = detailKeranjang
        )

        basketDB.child(AuthenticationRepository.fbAuth.currentUser?.uid!!).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun initEventListener() {
        basketDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataRef = arrayListOf<KeranjangModel>()

                    for (dataSS in snapshot.children) {
                        val data: KeranjangModel? = dataSS.getValue(
                            KeranjangModel::class.java
                        )
                        data?.keranjangId = dataSS.key.toString()
                        detailKeranjang = ArrayList(data!!.detailKeranjang)
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
                }

            })
    }

    fun addItem(listener: BasketListener, data: DetailKeranjangModel) {
        initEventListener()
        detailKeranjang.add(data)
        val keranjang = KeranjangModel()
        keranjang.userId = AuthenticationRepository.fbAuth.currentUser?.uid!!
        keranjang.keranjangId = AuthenticationRepository.fbAuth.currentUser?.uid!!
        keranjang.detailKeranjang = detailKeranjang
        updateDataKeranjang(listener, keranjang)
    }

    private fun updateDataKeranjang(listener: BasketListener, data: KeranjangModel) {
        val newData = data.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${FirebaseRef.KERANJANG_REF}/${data.keranjangId}" to newData
        )

        FirebaseDatabase.getInstance().reference.updateChildren(childUpdates).addOnSuccessListener {
            listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyInsertDataStatus(ModelContainer.getFailModel())
        }
    }

    fun getList(): LiveData<ModelContainer<ArrayList<KeranjangModel>>> {
        return basketList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }


}
