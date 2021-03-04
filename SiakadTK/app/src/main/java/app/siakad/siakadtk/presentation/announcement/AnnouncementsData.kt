package app.siakad.siakadtk.presentation.announcement
import app.siakad.siakadtk.domain.models.PengumumanModel

object AnnouncementsData {
    private val announcementsTitle = arrayOf(
        "Pembelian Buku & Seragam",
        "Pendaftaran Ulang Dibuka"
    )

    private val announcementsDesc = arrayOf(
        "Dapat dilakukan setelah pembayaran daftar ulang sebesar Rp.275.000 terverifikasi.",
        "Silahkan melakukan daftar ulang, sebelum tanggal 31 Januari.",
    )

    val listData: ArrayList<PengumumanModel>
        get() {
            val list = arrayListOf<PengumumanModel>()
            for (position in announcementsTitle.indices) {
                val announce = PengumumanModel()
                announce.judul = announcementsTitle[position]
                announce.keterangan = announcementsDesc[position]
                list.add(announce)
            }
            return list
        }
}