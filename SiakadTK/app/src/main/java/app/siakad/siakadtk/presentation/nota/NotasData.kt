package app.siakad.siakadtk.presentation.nota

import app.siakad.siakadtk.R
import app.siakad.siakadtk.data.db.childs.Pesanan
import app.siakad.siakadtk.domain.models.PesananModel

object NotasData {
    private val notasNames = arrayOf(
        "Nota Pemesanan 1",
        "Nota Pemesanan 2",
        "Nota Pemesanan 3"
    )

    private val notasProducts = arrayOf(
        "Seragam Olahraga, Buku Tema, Seragam Formal",
        "Seragam",
        "Buku"
    )

    private val notasDataAccepted = arrayOf(
        "21/01/2021",
        "21/02/2021",
        "21/03/2021"
    )

    private val notasTotalPrices = intArrayOf(
        100000,
        70000,
        100000)

    private val notasStatus = intArrayOf(
        R.drawable.ic_status_menunggu_pembayaran,
        R.drawable.ic_status_proses,
        R.drawable.ic_status_selesai,
        R.drawable.ic_status_menunggu_pembayaran)


    val listData: ArrayList<PesananModel>
        get() {
            val list = arrayListOf<PesananModel>()
            for (position in notasNames.indices) {
                val activity = PesananModel()
                activity.pesananId = notasNames[position]
//                activity.detailPesanan = notasProducts[position]
//                activity.dateAccepted = notasDataAccepted[position]
//                activity.totalPrice = notasTotalPrices[position]
//                activity.status = notasStatus[position]
                list.add(activity)
            }
            return list
        }
}