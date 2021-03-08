package app.siakad.siakadtk.data.repositories

import app.siakad.siakadtk.data.db.refs.User
import com.google.firebase.auth.FirebaseAuth

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
    }
}