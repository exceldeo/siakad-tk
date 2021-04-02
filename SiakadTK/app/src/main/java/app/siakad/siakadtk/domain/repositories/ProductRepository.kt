package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.product.BukuModel
import app.siakad.siakadtk.domain.models.product.SeragamModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProductRepository() {
    private var uniformList = MutableLiveData<ModelContainer<ArrayList<SeragamModel>>>()
    private var bookList = MutableLiveData<ModelContainer<ArrayList<BukuModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val uniformDB = FirebaseRef(FirebaseRef.SERAGAM_REF).getRef()
    private val bookDB = FirebaseRef(FirebaseRef.BUKU_REF).getRef()

    fun initUniformEventListener() {
        uniformDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<SeragamModel>()

                for (dataSS in snapshot.children) {
                    val data: SeragamModel? = dataSS.getValue(
                        SeragamModel::class.java)
                    data?.produkId = dataSS.key.toString()
                    dataRef.add(data!!)
                }

                uniformList.postValue(
                    ModelContainer(
                        status = ModelState.SUCCESS,
                        data = dataRef
                    )
                )
            }
        })
    }

    fun initBookEventListener() {
        bookDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<BukuModel>()

                for (dataSS in snapshot.children) {
                    val data: BukuModel? = dataSS.getValue(
                        BukuModel::class.java)
                    dataRef.add(data!!)
                }

                bookList.postValue(
                    ModelContainer(
                        status = ModelState.SUCCESS,
                        data = dataRef
                    )
                )
            }
        })
    }

    fun getUniformList(): LiveData<ModelContainer<ArrayList<SeragamModel>>> {
        return uniformList
    }

    fun getBookList(): LiveData<ModelContainer<ArrayList<BukuModel>>> {
        return bookList
    }

    fun getInsertState(): LiveData<ModelContainer<String>> {
        return insertState
    }
}