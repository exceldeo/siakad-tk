package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pesanan (
    var pesananId: String? = null,
    var namaSiswa: String? = null,
    var alamat: String? = null,
    var kelas: String? = null,
    var noHP: String? = null,
    var jumlah: Int = 0,
    var total: Int = 1000,
    var detailPesanan: Array<DetailPesanan>? = null
): Parcelable