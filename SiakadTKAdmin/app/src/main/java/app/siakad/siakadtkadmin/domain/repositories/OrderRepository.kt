package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PesananModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderRepository() {
    private var orderList = MutableLiveData<ModelContainer<ArrayList<PesananModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val orderDB = FirebaseRef(MainRepository.PESANAN_REF).getRef()

    fun initEventListener() {
        orderDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<PesananModel>()

                for (dataSS in snapshot.children) {
                    val data: PesananModel? = dataSS.getValue(PesananModel::class.java)
                    dataRef.add(data!!)
                }

                orderList.postValue(
                    ModelContainer(
                        status = ModelState.SUCCESS,
                        data = dataRef
                    )
                )
            }
        })
    }

    fun getOrderList(): LiveData<ModelContainer<ArrayList<PesananModel>>> {
        return orderList
    }
}