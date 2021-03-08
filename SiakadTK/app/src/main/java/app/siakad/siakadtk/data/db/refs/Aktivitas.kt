package app.siakad.siakadtk.data.db.refs

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Aktivitas (
    @get:Exclude
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: Date? = null
): Parcelable