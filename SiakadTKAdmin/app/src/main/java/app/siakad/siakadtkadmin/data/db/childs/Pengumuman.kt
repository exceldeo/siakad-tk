package app.siakad.siakadtkadmin.data.db.childs

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Pengumuman (
    @get:Exclude
    var pengumumanId: String? = null,
    var adminId: String? = null,
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: String? = null
): Parcelable