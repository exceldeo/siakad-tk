package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import app.siakad.siakadtk.domain.models.DetailPesananProdukModel
import app.siakad.siakadtk.domain.models.PesananModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pesanan (
    var pesanan: PesananModel
): Parcelable