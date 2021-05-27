package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListener
import com.google.firebase.database.*

class ProductRepository {
  private val eventListenersBook: ArrayList<Any> = arrayListOf()
  private val eventListenersUniform: ArrayList<Any> = arrayListOf()

  private val uniformDB = FirebaseRef(
    FirebaseRef.SERAGAM_REF
  ).getRef()

  private val bookDB = FirebaseRef(
    FirebaseRef.BUKU_REF
  ).getRef()

  fun initGetUniformEventListener(listener: ProductListListener) {
    uniformDB.addValueEventListener(object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {}

      override fun onDataChange(snapshot: DataSnapshot) {
        val dataRef = arrayListOf<SeragamModel>()

        for (dataSS in snapshot.children) {
          val data: SeragamModel? = dataSS.getValue(SeragamModel::class.java)
          data?.produkId = dataSS.key.toString()
          dataRef.add(data!!)

          listener.setUniformList(
            ModelContainer(
              status = ModelState.SUCCESS,
              data = dataRef
            )
          )
        }
      }
    })
  }

  fun initGetBookEventListener(listener: ProductListListener) {
    bookDB.addValueEventListener(object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {}

      override fun onDataChange(snapshot: DataSnapshot) {
        val dataRef = arrayListOf<BukuModel>()

        for (dataSS in snapshot.children) {
          val data: BukuModel? = dataSS.getValue(BukuModel::class.java)
          data?.produkId = dataSS.key.toString()
          dataRef.add(data!!)

          listener.setBookList(
            ModelContainer(
              status = ModelState.SUCCESS,
              data = dataRef
            )
          )
        }
      }
    })
  }

  fun initGetBookListById(
    listener: OrderDetailListener,
    bookId: String
  ) {
    val eventListener = bookDB.orderByKey().equalTo(bookId)
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
          val data: BukuModel? =
            snapshot.getValue(BukuModel::class.java)

          if (data != null) {
            data.produkId = snapshot.key.toString()
            listener.updateBukuItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val data: BukuModel? =
            snapshot.getValue(BukuModel::class.java)

          if (data != null) {
            data.produkId = snapshot.key.toString()
            listener.addBukuItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
          val data: BukuModel? =
            snapshot.getValue(BukuModel::class.java)

          if (data != null) {
            data.produkId = snapshot.key.toString()
            listener.removeBukuItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }
      })

    eventListenersBook.add(eventListener)
  }

  fun initGetUniformListById(
    listener: OrderDetailListener,
    uniformId: String
  ) {
    val eventListener = uniformDB.orderByKey().equalTo(uniformId)
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
          val data: SeragamModel? =
            snapshot.getValue(SeragamModel::class.java)

          if (data != null) {
            data.produkId = snapshot.key.toString()
            listener.updateSeragamItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val data: SeragamModel? =
            snapshot.getValue(SeragamModel::class.java)

          if (data != null) {
            data.produkId = snapshot.key.toString()
            listener.addSeragamItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
          val data: SeragamModel? =
            snapshot.getValue(SeragamModel::class.java)

          if (data != null) {
            data.produkId = snapshot.key.toString()
            listener.removeSeragamItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }
      })

    eventListenersUniform.add(eventListener)
  }

  fun insertDataSeragam(listener: ProductListener, data: SeragamModel) {
    val newKey = uniformDB.push().key.toString()
    data.adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!
    data.produkId = newKey

    uniformDB.child(newKey).setValue(data).addOnSuccessListener {
      listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyInsertDataStatus(ModelContainer.getFailModel())
    }
  }

  fun updateDataSeragam(listener: Any, data: SeragamModel) {
    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.produkId}" to newData
    )

    uniformDB.updateChildren(childUpdates).addOnSuccessListener {
      if (listener is ProductListener) {
        listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
      }
    }.addOnFailureListener {
      if (listener is ProductListener) {
        listener.notifyInsertDataStatus(ModelContainer.getFailModel())
      }
    }
  }

  fun insertDataBuku(listener: ProductListener, data: BukuModel) {
    val newKey = bookDB.push().key.toString()
    data.adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!
    data.produkId = newKey

    bookDB.child(newKey).setValue(data).addOnSuccessListener {
      listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyInsertDataStatus(ModelContainer.getFailModel())
    }
  }

  fun updateDataBuku(listener: Any, data: BukuModel) {
    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.produkId}" to newData
    )

    bookDB.updateChildren(childUpdates).addOnSuccessListener {
      if (listener is ProductListener) {
        listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
      }
    }.addOnFailureListener {
      if (listener is ProductListener) {
        listener.notifyInsertDataStatus(ModelContainer.getFailModel())
      }
    }
  }

  fun removeEventListener() {
    eventListenersBook.forEach {
      if (it is ChildEventListener) {
        bookDB.removeEventListener(it)
      } else if (it is ValueEventListener) {
        bookDB.removeEventListener(it)
      }
    }

    eventListenersUniform.forEach {
      if (it is ChildEventListener) {
        uniformDB.removeEventListener(it)
      } else if (it is ValueEventListener) {
        uniformDB.removeEventListener(it)
      }
    }
  }
}