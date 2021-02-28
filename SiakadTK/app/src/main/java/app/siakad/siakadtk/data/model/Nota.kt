package app.siakad.siakadtk.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nota (
    var title: String? = null,
    var product: String? = null,
    var dateAccepted: String? = null,
    var totalPrice: Int = 0,
    var status: Int = 0
): Parcelable