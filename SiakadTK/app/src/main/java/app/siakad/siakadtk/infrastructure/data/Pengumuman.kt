package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Pengumuman (
    var pengumumanId: String = "",
    var tipe: String = "",
    var tujuanId: String = "",
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = ""
): Parcelable