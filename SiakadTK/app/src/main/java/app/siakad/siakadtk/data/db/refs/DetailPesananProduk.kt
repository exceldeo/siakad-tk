package app.siakad.siakadtk.data.db.refs

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPesananProduk (
    @get:Exclude
    var detialPesananId: String? = null,
    var pesananId: String? = null,
    var ukuran: String? = null,
    var jumlah: Int = 0,
    var harga: Int = 0,
    var status: String? = null
): Parcelable