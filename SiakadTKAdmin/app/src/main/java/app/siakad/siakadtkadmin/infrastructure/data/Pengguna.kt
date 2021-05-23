package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import app.siakad.siakadtkadmin.domain.models.DetailKeranjangModel
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pengguna(
    var pengguna: PenggunaModel,
    var kelas: KelasModel
) : Parcelable