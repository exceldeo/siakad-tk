package app.siakad.siakadtk.data.db.refs

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlang (
    @get:Exclude
    var dafulId: String? = null,
    var tanggal: String? = null,
    var status: String? = null,
    var fotoBayar: String? = null
): Parcelable