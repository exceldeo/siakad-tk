package app.siakad.siakadtk.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Product (
    var img: Int = 0,
    var title: String? = null,
    var dateDeadline: Date? = null,
    var price: Int = 0,
    var status: String? = null
): Parcelable