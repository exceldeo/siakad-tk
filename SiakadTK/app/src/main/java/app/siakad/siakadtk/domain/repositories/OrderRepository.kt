package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderRepository() {
    private var orderList = MutableLiveData<ModelContainer<ArrayList<PesananModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val orderDB = FirebaseRef(FirebaseRef.PESANAN_REF).getRef()

    fun initEventListener() {
        orderDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<PesananModel>()

                for (dataSS in snapshot.children) {
                    val data: PesananModel? = dataSS.getValue(PesananModel::class.java)
                    data?.pesananId = dataSS.key.toString()
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

    fun insertDataPesanan(data: Pesanan) {
        val newKey = orderDB.push().key.toString()
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val newData =
            PesananModel(
                pesananId = newKey,
                detailPesananProdukId = data.detailPesananProduk,
                userId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
                tanggalPesan = todayDate,
                statusPesan = OrderStateModel.WAITPAYMENT.toString()
            )
    }
    fun getOrderList(): LiveData<ModelContainer<ArrayList<PesananModel>>> {
        return orderList
    }
}