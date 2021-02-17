package app.siakad.siakadtkadmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Notification (
    var judul: String? = null,
    var isi: String? = null,
    var tanggal: Date? = null
): Parcelable