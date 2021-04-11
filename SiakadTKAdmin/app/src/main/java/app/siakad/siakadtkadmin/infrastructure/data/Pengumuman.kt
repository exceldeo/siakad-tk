package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pengumuman (
    var pengumumanId: String = "",
    var tipe: String = "",
    var tujuanId: String = "",
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = ""
): Parcelable