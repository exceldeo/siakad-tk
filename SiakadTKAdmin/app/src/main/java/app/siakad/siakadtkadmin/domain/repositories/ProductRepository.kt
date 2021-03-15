package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.ModelState
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.infrastructure.data.product.Buku
import app.siakad.siakadtkadmin.infrastructure.data.product.Seragam
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProductRepository() {
    private var uniformList = MutableLiveData<ModelContainer<ArrayList<SeragamModel>>>()
    private var bookList = MutableLiveData<ModelContainer<ArrayList<BukuModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val uniformDB = FirebaseRef(MainRepository.SERAGAM_REF).getRef()
    private val bookDB = FirebaseRef(MainRepository.BUKU_REF).getRef()

    fun initUniformEventListener() {
        uniformDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<SeragamModel>()

                for (dataSS in snapshot.children) {
                    val data: SeragamModel? = dataSS.getValue(
                        SeragamModel::class.java)
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

    fun insertDataSeragam(data: Seragam) {
        val newKey = uniformDB.push().key.toString()
        val newData =
            SeragamModel(
                produkId = newKey,
                adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
                fotoProduk = data.fotoProduk,
                jenisKelamin = data.jenisKelamin,
                namaProduk = data.namaProduk,
                detailSeragam = data.detailSeragam,
                jumlah = data.jumlah
            )

        uniformDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
    }

    fun insertDataBuku(data: Buku) {
        val newKey = bookDB.push().key.toString()
        val newData = BukuModel(
            produkId = newKey,
            adminId = AuthenticationRepository.fbAuth.currentUser?.uid!!,
            fotoProduk = data.fotoProduk,
            namaProduk = data.namaProduk,
            harga = data.harga,
            jumlah = data.jumlah
        )

        bookDB.child(newKey).setValue(newData).addOnSuccessListener {
            insertState.postValue(ModelContainer.getSuccesModel("Success"))
        }.addOnFailureListener {
            insertState.postValue(ModelContainer.getFailModel())
        }
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