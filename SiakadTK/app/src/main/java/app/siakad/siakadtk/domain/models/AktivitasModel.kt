package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class AktivitasModel (
    var judul: String? = null,
    var keterangan: String? = null,
    var tanggal: Date? = null
): Parcelable