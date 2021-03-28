package app.siakad.siakadtk.presentation.screens.profile

import app.siakad.siakadtk.infrastructure.data.Aktivitas

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

    val listData: ArrayList<Aktivitas>
        get() {
            val list = arrayListOf<Aktivitas>()
            for (position in userActivitiesNames.indices) {
                val activity = Aktivitas()
                activity.judul = userActivitiesNames[position]
                activity.keterangan = userActivitiesDetails[position]
//                activity.date = userActivitiesDates[position]
                list.add(activity)
            }
            return list
        }
}