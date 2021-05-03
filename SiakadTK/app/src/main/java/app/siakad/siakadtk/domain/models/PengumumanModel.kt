package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.firebase.database.Exclude

@Parcelize
data class PengumumanModel (
    @get: Exclude
    var pengumumanId: String = "",
    var adminId: String = "",
    var tipe: String = "",
    var tujuanId: String = "",
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = ""
): Parcelable