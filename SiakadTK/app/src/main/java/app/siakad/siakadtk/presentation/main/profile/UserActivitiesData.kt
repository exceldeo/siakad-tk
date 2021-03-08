package app.siakad.siakadtk.presentation.main.profile

import app.siakad.siakadtk.domain.models.AktivitasModel

object UserActivitiesData {
    private val userActivitiesNames = arrayOf(
        "Registrasi Ulang Telah Disetujui",
        "Pesanan Telah Dikonfirmasi",
        "Pesanan Telah Dikonfirmasi"
    )

    private val userActivitiesDetails = arrayOf(
        "Daftar Ulang",
        "Seragam",
        "Buku"
    )

//    private val userActivitiesDates = arrayOf(
//        "21/02/2021",
//        "21/02/2021",
//        "21/02/2021"
//    )

    val listData: ArrayList<AktivitasModel>
        get() {
            val list = arrayListOf<AktivitasModel>()
            for (position in userActivitiesNames.indices) {
                val activity = AktivitasModel()
                activity.judul = userActivitiesNames[position]
                activity.keterangan = userActivitiesDetails[position]
//                activity.date = userActivitiesDates[position]
                list.add(activity)
            }
            return list
        }
}