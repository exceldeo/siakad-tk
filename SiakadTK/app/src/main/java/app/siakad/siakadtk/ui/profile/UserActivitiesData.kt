package app.siakad.siakadtk.ui.profile

import app.siakad.siakadtk.data.model.UserActivities

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

    val listData: ArrayList<UserActivities>
        get() {
            val list = arrayListOf<UserActivities>()
            for (position in userActivitiesNames.indices) {
                val activity = UserActivities()
                activity.title = userActivitiesNames[position]
                activity.desc = userActivitiesDetails[position]
//                activity.date = userActivitiesDates[position]
                list.add(activity)
            }
            return list
        }
}