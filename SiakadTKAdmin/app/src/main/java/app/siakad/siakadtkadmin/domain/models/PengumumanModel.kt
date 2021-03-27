package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PengumumanModel (
    @get:Exclude
    var pengumumanId: String = "",
    var adminId: String = "",
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = ""
): Parcelable