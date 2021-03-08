package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notifikasi (
    var notifikasiId: String? = null,
    var userId: String? = null,
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: String? = null
): Parcelable