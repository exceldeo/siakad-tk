package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Siswa (
    var userId: String? = null,
    var nama: String? = null,
    var alamat: String? = null,
    var noHP: String? = null,
    var email: String? = null,
    var passwd: String? = null,
    var fotoBayarAwal: String? = null
): Parcelable