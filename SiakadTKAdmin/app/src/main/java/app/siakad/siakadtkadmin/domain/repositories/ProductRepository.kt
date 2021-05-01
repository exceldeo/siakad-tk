package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListener
import app.siakad.siakadtkadmin.infrastructure.data.product.Seragam
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepository {
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

  fun updateDataSeragam(listener: ProductListener, data: SeragamModel) {
    data.adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!
    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.produkId}" to newData
    )

    uniformDB.updateChildren(childUpdates).addOnSuccessListener {
      listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyInsertDataStatus(ModelContainer.getFailModel())
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

  fun updateDataBuku(listener: ProductListener, data: BukuModel) {
    data.adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!
    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.produkId}" to newData
    )

    bookDB.updateChildren(childUpdates).addOnSuccessListener {
      listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyInsertDataStatus(ModelContainer.getFailModel())
    }
  }
}