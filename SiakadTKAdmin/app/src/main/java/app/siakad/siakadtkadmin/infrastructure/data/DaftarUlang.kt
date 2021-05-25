package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import app.siakad.siakadtkadmin.domain.models.DaftarUlangModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlang (
    var pengguna: Pengguna,
    var daful: DaftarUlangModel
): Parcelable