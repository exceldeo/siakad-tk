package app.siakad.siakadtkadmin.data.repositories

import app.siakad.siakadtkadmin.data.db.refs.User

class MainRepository {
    companion object {
        const val USER_REF = "User"
        const val USER_DETAIL_REF = "UserDetail"
        const val PENGUMUMAN_REF = "Pengumuman"
        const val NOTIFIKASI_REF = "Notifikasi"
        const val DAFTAR_ULANG_REF = "DaftarUlang"
        const val PESANAN_REF = "Pesanan"
        const val PRODUK_REF = "Produk"
        const val DETAIL_PESANAN_PRODUK_REF = "DetailPesananProduk"

        lateinit var currentUser: User

        fun setUser(userId: String, email: String, passwd: String) {
            currentUser = User(userId = userId, email = email, passwd = passwd)
        }
    }
}