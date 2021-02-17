package app.siakad.siakadtk.ui.product

import app.siakad.siakadtk.R

object ProductsData {
    private val productNames = arrayOf(
        "Buku Mengenal Hewan",
        "Buku Tema 5"
    )

    private val productStatus = arrayOf(
        "Wajib",
        "Opsional"
    )

    private val productDeadline = arrayOf(
        "21/01/2021",
        "21/02/2021"
    )

    private val productPrice = intArrayOf(
        100000,
        70000)

    private val productImg = intArrayOf(
        R.drawable.example_image,
        R.drawable.example_image
    )
    val listData: ArrayList<Product>
        get() {
            val list = arrayListOf<Product>()
            for (position in productNames.indices) {
                val book = Product()
                book.title = productNames[position]
                book.status = productStatus[position]
                book.dateDeadline = productDeadline[position]
                book.price = productPrice[position]
                book.img = productImg[position]
                list.add(book)
            }
            return list
        }
}