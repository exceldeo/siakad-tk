package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FiturModel (
    var daful: Boolean = true,
    var pesanan: Boolean = true
): Parcelable