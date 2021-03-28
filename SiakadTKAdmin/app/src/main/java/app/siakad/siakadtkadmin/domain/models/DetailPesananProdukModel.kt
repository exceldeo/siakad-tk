package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPesananProdukModel (
    @get:Exclude
    var detailPesananId: String = "",
    var pesananId: String = "",
    var produkId: String = "",
    var ukuran: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0,
    var status: String = ""
): Parcelable