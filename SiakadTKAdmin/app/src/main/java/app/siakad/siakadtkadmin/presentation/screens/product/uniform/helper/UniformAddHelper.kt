package app.siakad.siakadtkadmin.presentation.screens.product.uniform.helper

interface UniformAddHelper {
    fun insertData(ukuran: String, jumlah: Int, harga: Int)
    fun deleteData(pos: Int)
}