package app.siakad.siakadtk.data.db.refs

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.firebase.database.Exclude

@Parcelize
data class Pengumuman (
    @get: Exclude
    var pengumumanId: String? = null,
    var adminId: String? = null,
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: String? = null
): Parcelable