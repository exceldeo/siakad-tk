package app.siakad.siakadtkadmin.domain.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AnnouncementRepository(private val context: Context) {
    private var announcementList = MutableLiveData<ArrayList<app.siakad.siakadtkadmin.infrastructure.data.Pengumuman>>()

    companion object {
        val announcementDB = FirebaseRef(
            MainRepository.PENGUMUMAN_REF
        ).getRef()
    }

    fun initEventListener() {
        announcementDB.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataRef = arrayListOf<Pengumuman>()

                for (dataSS in snapshot.children) {
                    val data: Pengumuman? = dataSS.getValue(Pengumuman::class.java)

                    dataRef.add(
                        Pengumuman(
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

    fun insertData(data: Pengumuman) {
        val newKey = announcementDB.getKey().toString()
        val newData = PengumumanModel(
            pengumumanId = newKey,
            adminId = AuthenticationRepository.currentUser.userId,
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

    fun getAnnouncementList(): LiveData<ArrayList<app.siakad.siakadtkadmin.infrastructure.data.Pengumuman>> {
        return announcementList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}