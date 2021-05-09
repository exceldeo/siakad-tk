package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderRepository() {
    private val orderDB = FirebaseRef(
        FirebaseRef.PESANAN_REF
    ).getRef()

    fun initEventListener(listener: OrderListListener) {
        orderDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<PesananModel>()

                for (dataSS in snapshot.children) {
                    val data: PesananModel? = dataSS.getValue(PesananModel::class.java)
                    data?.pesananId = dataSS.key.toString()
                    dataRef.add(data!!)

                    listener.setOrderList(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = dataRef
                        )
                    )
                }
            }
        })
    }

    fun updateOrderData(listener: OrderDetailListener, data: PesananModel) {
        val newData = data.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${data.pesananId}" to newData
        )

        orderDB.updateChildren(childUpdates).addOnSuccessListener {
            listener.notifyOrderChangeStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyOrderChangeStatus(ModelContainer.getFailModel())
        }
    }
}