package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.KeranjangModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.basket.BasketListener
import com.google.firebase.database.*

class BasketRepository() {
    private val basketDB = FirebaseRef(FirebaseRef.KERANJANG_REF).getRef()
    private var detailKeranjang = arrayListOf<DetailKeranjangModel>()

    fun initEventListener(listener: BasketListener) {
        basketDB.orderByChild("userId").equalTo(AuthenticationRepository.fbAuth.currentUser?.uid!!).addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: KeranjangModel? = snapshot.getValue(
                        KeranjangModel::class.java
                    )
                    data?.keranjangId = snapshot.key.toString()
                    detailKeranjang = ArrayList(data!!.detailKeranjang)

                    listener.addBasketItem(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = ArrayList(data!!.detailKeranjang)
                        )
                    )
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val data: KeranjangModel? = snapshot.getValue(
                        KeranjangModel::class.java
                    )
                    data?.keranjangId = snapshot.key.toString()
                    detailKeranjang = ArrayList(data!!.detailKeranjang)

                    listener.setBasketList(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = ArrayList(data!!.detailKeranjang)
                        )
                    )
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val data: KeranjangModel? = snapshot.getValue(
                        KeranjangModel::class.java
                    )
                    data?.keranjangId = snapshot.key.toString()
                    detailKeranjang = ArrayList(data!!.detailKeranjang)

                    listener.removeBasketItem(
                        ModelContainer(
                            status = ModelState.SUCCESS,
                            data = ArrayList(data!!.detailKeranjang)
                        )
                    )
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun addItem(listener: BasketListener, data: DetailKeranjangModel) {
        detailKeranjang.add(data)
        val keranjang = KeranjangModel()
        keranjang.userId = AuthenticationRepository.fbAuth.currentUser?.uid!!
        keranjang.keranjangId = AuthenticationRepository.fbAuth.currentUser?.uid!!
        keranjang.detailKeranjang = detailKeranjang
        updateDataKeranjang(listener, keranjang)
    }

    fun resetKeranjang(listener: BasketListener){
        val keranjang = KeranjangModel()
        keranjang.userId = AuthenticationRepository.fbAuth.currentUser?.uid!!
        keranjang.keranjangId = AuthenticationRepository.fbAuth.currentUser?.uid!!
        keranjang.detailKeranjang = arrayListOf()
        val newData = keranjang.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${FirebaseRef.KERANJANG_REF}/${keranjang.keranjangId}" to newData
        )

        FirebaseDatabase.getInstance().reference.updateChildren(childUpdates).addOnSuccessListener {
            listener.notifyInsertDataStatus(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            listener.notifyInsertDataStatus(ModelContainer.getFailModel())
        }
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
}
