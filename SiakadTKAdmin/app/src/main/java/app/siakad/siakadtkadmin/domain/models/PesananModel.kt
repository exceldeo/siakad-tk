package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PesananModel (
    var pesananId: String? = null,
    var namaSiswa: String? = null,
    var alamat: String? = null,
    var kelas: String? = null,
    var noHP: String? = null,
    var jumlah: Int = 0,
    var total: Int = 1000,
    var detailPesanan: Array<DetailPesananModel>? = null
): Parcelable