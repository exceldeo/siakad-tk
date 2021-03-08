package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlang (
    var dafulId: String? = null,
    var userId: String? = null,
    var namaSiswa: String? = null,
    var kelas: String? = null,
    var jenisKelamin: String? = null,
    var namaWali: String? = null,
    var alamat: String? = null,
    var noHP: String? = null,
    var nominalbayar: Int = 1000000,
    var fotoBayar: String? = null
): Parcelable