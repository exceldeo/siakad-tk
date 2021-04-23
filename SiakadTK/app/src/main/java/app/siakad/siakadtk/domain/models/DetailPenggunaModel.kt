package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPenggunaModel (
    @get:Exclude
    var userDetailId: String = "",
    var userId: String = "",
    var nama: String = "",
    var kelas: String = "",
    var thnAjaran : String = "",
    var jenisKelamin: String = "",
    var tanggalLahir: String = "",
    var alamat: String = "",
    var noHP: String = "",
    var namaOrtu: String = "",
    var userState: Boolean = false,
    var dafulState: Boolean = false
): Parcelable