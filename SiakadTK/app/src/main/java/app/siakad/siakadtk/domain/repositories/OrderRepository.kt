package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.domain.utils.listeners.order.OrderListListener
import app.siakad.siakadtk.domain.utils.listeners.order.OrderListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderRepository() {
    private var orderList = MutableLiveData<ModelContainer<ArrayList<PesananModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()
    private val orderDB = FirebaseRef(FirebaseRef.PESANAN_REF).getRef()

    fun initGetOrderEventListener(listener: OrderListListener)
    {
        orderDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!)
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: PesananModel? =
                        snapshot.getValue(PesananModel::class.java)

                    if (data != null) {
                        data.pesananId = snapshot.key.toString()
                        listener.updateOrderItem(
                            ModelContainer(
                                status = ModelState.SUCCESS,
                                data = data
                            )
                        )
                    }
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: PesananModel? =
                        snapshot.getValue(PesananModel::class.java)

                    if (data != null) {
                        data.pesananId = snapshot.key.toString()
                        listener.addOrderItem(
                            ModelContainer(
                                status = ModelState.SUCCESS,
                                data = data
                            )
                        )
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }
            })
    }

    fun insertDataPesanan(listener: OrderListener, data: ArrayList<DetailKeranjangModel>) {
        val newKey = orderDB.push().key.toString()
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val newData =
            PesananModel(
                pesananId = newKey,
                detailPesanan = data,
                userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
                tanggalPesan = todayDate,
                statusPesan = OrderStateModel.ORDER_PENDING.str,
                fotoBayar = ""
            )

        orderDB.child(newKey).setValue(newData).addOnSuccessListener {
            listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyInsertDataStatus(ModelContainer.getFailModel())
        }
    }

    fun updateOrderData(listener: OrderListener, data: PesananModel) {
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

    fun getOrderList(): LiveData<ModelContainer<ArrayList<PesananModel>>> {
        return orderList
    }

    fun insertPaymentImage(listener: OrderListener, data: PesananModel) {
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