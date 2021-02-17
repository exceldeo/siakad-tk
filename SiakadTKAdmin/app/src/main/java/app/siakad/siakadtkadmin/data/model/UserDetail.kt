package app.siakad.siakadtkadmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetail (
    var namaAktivitas: String? = null,
    var status: Boolean = false
): Parcelable