package app.siakad.siakadtkadmin.data.db.childs

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fitur (
    var daful: Boolean = true,
    var pesanan: Boolean = true
): Parcelable