package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUserModel (
    @get:Exclude
    var userDetailId: String = "",
    var userId: String = "",
    var tanggalLahir: String = "",
    var tahunAjaran: String = "",
    var namaOrtu: String = "",
    var fotoSiswa: String = "",
    var fotoBayarAwal: String = "",
    var jenisKelamin: String = "",
    var kelas: String = "",
    var dafulState: Boolean = false
): Parcelable