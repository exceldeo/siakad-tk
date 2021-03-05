package app.siakad.siakadtk.data.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.R
import app.siakad.siakadtk.data.db.FirebaseRef
import app.siakad.siakadtk.data.db.refs.Pengumuman
import app.siakad.siakadtk.domain.models.PengumumanModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AnnouncementRepository(private val context: Context) {
    private var announcementList = MutableLiveData<ArrayList<PengumumanModel>>()
    private val announcementDB = FirebaseRef<Pengumuman>(MainRepository.PENGUMUMAN_REF, context)

    fun getAllData() {
        announcementDB.getRef().addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                showToast(context.getString(R.string.fail_get_data))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<PengumumanModel>()

                for (dataSS in snapshot.children) {
                    val data: Pengumuman? = dataSS.getValue(Pengumuman::class.java)

                    dataRef.add(
                        PengumumanModel(
                            pengumumanId = data?.pengumumanId,
                            judul = data?.judul,
                            keterangan = data?.keterangan,
                            tanggal = data?.tanggal
                        )
                    )
                }

                announcementList.postValue(dataRef)

                showToast(context.getString(R.string.scs_get_data))
            }
        })
    }

    fun insertData(data: PengumumanModel) {
        val newKey = announcementDB.getKey().toString()
        val newData = Pengumuman(
            pengumumanId = newKey,
            adminId = MainRepository.currentUser.userId,
            judul = data.judul,
            keterangan = data.keterangan,
            tanggal = data.tanggal
        )

        announcementDB.getRef().child(newKey).setValue(newData).addOnSuccessListener {
            showToast(context.getString(R.string.scs_set_data))
        }.addOnFailureListener {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

    fun getAnnouncementList(): LiveData<ArrayList<PengumumanModel>> {
        return announcementList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}