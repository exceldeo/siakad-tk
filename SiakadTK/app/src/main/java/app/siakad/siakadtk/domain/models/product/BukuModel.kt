package app.siakad.siakadtk.domain.models.product

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class BukuModel (
    @get:Exclude
    var produkId: String = "",
    var adminId: String = "",
    var namaProduk: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0
): Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "adminId" to adminId,
            "namaProduk" to namaProduk,
            "fotoProduk" to fotoProduk,
            "jumlah" to jumlah,
            "harga" to harga
        )
    }
}