package app.siakad.siakadtkadmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var fotoSiswa: String? = null,
    var namaSiswa: String? = null,
    var emailSiswa: String? = null,
    var tanggalRegisAkun: String? = null
): Parcelable