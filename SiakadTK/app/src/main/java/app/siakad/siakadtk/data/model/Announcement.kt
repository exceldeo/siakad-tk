package app.siakad.siakadtk.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Announcement (
    var title: String? = null,
    var desc: String? = null,
    var date: Date? = null
): Parcelable