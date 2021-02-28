package app.siakad.siakadtk.ui.announcement

import app.siakad.siakadtk.data.model.Announcement

object AnnouncementsData {
    private val announcementsTitle = arrayOf(
        "Pembelian Buku & Seragam",
        "Pendaftaran Ulang Dibuka"
    )

    private val announcementsDesc = arrayOf(
        "Dapat dilakukan setelah pembayaran daftar ulang sebesar Rp.275.000 terverifikasi.",
        "Silahkan melakukan daftar ulang, sebelum tanggal 31 Januari.",
    )

    val listData: ArrayList<Announcement>
        get() {
            val list = arrayListOf<Announcement>()
            for (position in announcementsTitle.indices) {
                val announce = Announcement()
                announce.title = announcementsTitle[position]
                announce.desc = announcementsDesc[position]
                list.add(announce)
            }
            return list
        }
}