package app.siakad.siakadtk.infrastructure.data

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPengguna(
    @get:Exclude
    var tanggalLahir: String = "",
    var tahunAjaran: String = "",
    var namaOrtu: String = "",
    var fotoSiswa: String = "",
    var fotoBayarAwal: String = "",
    var jenisKelamin: String = "",
    var kelas: String = "",
): Parcelable