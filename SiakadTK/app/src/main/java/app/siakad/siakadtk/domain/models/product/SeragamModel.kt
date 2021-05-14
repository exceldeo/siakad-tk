package app.siakad.siakadtk.domain.models.product

import android.os.Parcelable
import app.siakad.siakadtk.domain.models.product.DetailSeragamModel
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class SeragamModel (
    @get:Exclude
    var produkId: String = "",
    var adminId: String = "",
    var namaProduk: String = "",
    var jenisKelamin: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var detailSeragam: ArrayList<DetailSeragamModel> = arrayListOf()
): Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "produkId" to produkId,
            "adminId" to adminId,
            "namaProduk" to namaProduk,
            "jenisKelamin" to jenisKelamin,
            "fotoProduk" to fotoProduk,
            "jumlah" to jumlah,
            "detailSeragam" to detailSeragam,
        )
    }
}