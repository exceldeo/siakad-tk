package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Aktivitas (
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = ""
): Parcelable