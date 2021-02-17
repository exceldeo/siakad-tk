package app.siakad.siakadtkadmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Order (
    var foto: String? = null,
    var namaSiswa: String? = null,
    var kelas: String? = null,
    var jumlahPasanan: String? = null,
    var tanggalPesan: String? = null
): Parcelable