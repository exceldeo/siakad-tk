package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notifikasi (
    var notifikasiId: String = "",
    var userId: String = "",
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = ""
): Parcelable