package app.siakad.siakadtk.domain.repositories

import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.product.BukuModel
import app.siakad.siakadtk.domain.models.product.SeragamModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProductRepository() {
    private val uniformDB = FirebaseRef(FirebaseRef.SERAGAM_REF).getRef()
    private val bookDB = FirebaseRef(FirebaseRef.BUKU_REF).getRef()

    fun initGetUniformEventListener(listener: ProductListListener) {
        uniformDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<SeragamModel>()

                for (dataSS in snapshot.children) {
                    val data: SeragamModel? = dataSS.getValue(SeragamModel::class.java)
                    data?.produkId = dataSS.key.toString()

                    if (data?.jumlah!! <= 0) continue

                    dataRef.add(data)

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

                    if (data?.jumlah!! <= 0) continue

                    dataRef.add(data)

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

    fun updateDataBuku(listener: ProductListListener, data: BukuModel) {
        val newData = data.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${data.produkId}" to newData
        )

        bookDB.updateChildren(childUpdates).addOnSuccessListener {
            listener.notifyUpdateDataStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyUpdateDataStatus(ModelContainer.getFailModel())
        }
    }

    fun updateDataSeragam(listener: ProductListListener, data: SeragamModel) {
        val newData = data.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${data.produkId}" to newData
        )

        uniformDB.updateChildren(childUpdates).addOnSuccessListener {
            listener.notifyUpdateDataStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyUpdateDataStatus(ModelContainer.getFailModel())
        }
    }
}