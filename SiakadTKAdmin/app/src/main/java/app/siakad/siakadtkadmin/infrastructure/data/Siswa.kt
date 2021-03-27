package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.annotation.Inherited

@Parcelize
data class Siswa (
    var userId: String = "",
    var nama: String = "",
    var alamat: String = "",
    var noHP: String = "",
    var email: String = "",
    var passwd: String = ""
): Parcelable {
    override fun toString(): String = nama
}