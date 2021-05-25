package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPenggunaModel(
  @get:Exclude
  var userDetailId: String = "",
  var tanggalLahir: String = "",
  var namaOrtu: String = "",
  var fotoSiswa: String = "",
  var fotoBayarAwal: String = "",
  var jenisKelamin: String = "",
  var kelasId: String = "",
  var dafulState: Boolean = false
) : Parcelable