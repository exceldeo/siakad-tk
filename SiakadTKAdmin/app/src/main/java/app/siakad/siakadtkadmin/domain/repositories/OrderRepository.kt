package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderListListener
import app.siakad.siakadtkadmin.presentation.screens.order.OrderListFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderRepository {
  private val eventListeners: ArrayList<Any> = arrayListOf()

  private val orderDB = FirebaseRef(
    FirebaseRef.PESANAN_REF
  ).getRef()

  fun initGetOrderEventListener(
    listener: OrderListListener,
    type: String = OrderListFragment.ORDER_PENDING
  ) {
    val eventListener = orderDB.orderByChild("statusPesan").equalTo(type)
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
          val data: PesananModel? =
            snapshot.getValue(PesananModel::class.java)

          if (data != null) {
            data.pesananId = snapshot.key.toString()
            listener.removeOrderItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }
      })

    eventListeners.add(eventListener)
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

  fun removeOrderData(listener: OrderDetailListener, data: PesananModel) {
    orderDB.child(data.pesananId).removeValue().addOnSuccessListener {
      listener.notifyOrderDeleteStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener { listener.notifyOrderDeleteStatus(ModelContainer.getFailModel()) }
  }

  fun removeEventListener() {
    eventListeners.forEach {
      if (it is ChildEventListener) {
        orderDB.removeEventListener(it)
      } else if (it is ValueEventListener) {
        orderDB.removeEventListener(it)
      }
    }
  }
}