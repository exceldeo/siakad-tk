package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pengguna (
    var userId: String = "",
    var nama: String = "",
    var alamat: String = "",
    var noHP: String = "",
    var email: String = "",
    var passwd: String = "",
    var role: String = "",
    var status: Boolean = false,
    var detail: DetailPenggunaModel? = null
): Parcelable {
    override fun toString(): String = nama
}