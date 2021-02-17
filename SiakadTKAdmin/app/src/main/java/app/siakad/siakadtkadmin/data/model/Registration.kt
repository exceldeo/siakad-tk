package app.siakad.siakadtkadmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Registration (
    var fotoSiswa: String? = null,
    var namaSiswa: String? = null,
    var kelas: String? = null,
    var alamat: String? = null,
    var tanggalRegis: String? = null
): Parcelable