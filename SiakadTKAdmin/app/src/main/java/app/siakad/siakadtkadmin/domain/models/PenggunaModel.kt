package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PenggunaModel (
    @get:Exclude
    var userId: String = "",
    var nama: String = "",
    var alamat: String = "",
    var noHP: String = "",
    var email: String = "",
    var passwd: String = "",
    var role: String = "",
    var status: Boolean = false,
    var detailPengguna: DetailPenggunaModel? = null
): Parcelable {
    override fun toString(): String = nama

    fun pairNameId(): Map<String, String> {
        return mapOf(nama to userId)
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nama" to nama,
            "alamat" to alamat,
            "noHP" to noHP,
            "email" to email,
            "passwd" to passwd,
            "role" to role,
            "status" to status,
            "detailPengguna" to detailPengguna
        )
    }
}