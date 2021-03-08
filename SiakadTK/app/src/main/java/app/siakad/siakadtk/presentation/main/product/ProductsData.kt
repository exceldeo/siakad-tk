package app.siakad.siakadtk.presentation.main.product

import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.ProdukModel

object ProductsData {
    private val productNames = arrayOf(
        "Buku Mengenal Hewan",
        "Buku Tema 5"
    )

    private val productStatus = arrayOf(
        "Wajib",
        "Opsional"
    )

    private val productPrice = intArrayOf(
        100000,
        70000)

    private val productImg = intArrayOf(
        R.drawable.example_image,
        R.drawable.example_image
    )
    val listData: ArrayList<ProdukModel>
        get() {
            val list = arrayListOf<ProdukModel>()
            for (position in productNames.indices) {
                val product = ProdukModel()
                product.namaProduk = productNames[position]
                product.fotoProduk = productImg[position]
                list.add(product)
            }
            return list
        }
}