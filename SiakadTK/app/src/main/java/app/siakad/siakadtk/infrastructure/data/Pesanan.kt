package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import app.siakad.siakadtk.domain.models.DetailPesananModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pesanan (
    var pesananId: String = "",
    var namaSiswa: String = "",
    var alamat: String = "",
    var kelas: String = "",
    var noHP: String = "",
    var jumlah: Int = 0,
    var total: Int = 1000,
    var detailPesanan: ArrayList<DetailPesananModel>? = arrayListOf()
): Parcelable