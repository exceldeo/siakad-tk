package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PengumumanModel (
    @get:Exclude
    var pengumumanId: String? = null,
    var adminId: String? = null,
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: String? = null
): Parcelable