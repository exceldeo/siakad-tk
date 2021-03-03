package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlangModel (
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