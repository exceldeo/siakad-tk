package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class AktivitasModel (
    @get:Exclude
    var aktivitasId: String = "",
    var userId: String = "",
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = ""
): Parcelable