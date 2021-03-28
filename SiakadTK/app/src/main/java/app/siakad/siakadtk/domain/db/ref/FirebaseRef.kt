package app.siakad.siakadtk.domain.db.ref

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseRef(private val refName: String) {
    companion object {
        const val USER_REF = "User"
        const val USER_DETAIL_REF = "UserDetail"
        const val PENGUMUMAN_REF = "Pengumuman"
        const val DAFTAR_ULANG_REF = "DaftarUlang"
        const val NOTIFIKASI_REF = "Notifikasi"
        const val PESANAN_REF = "Pesanan"
        const val PRODUK_REF = "Produk"
        const val SERAGAM_REF = "Seragam"
        const val BUKU_REF = "Buku"
        const val KERANJANG_REF = "Keranjang"
        const val DETAIL_PESANAN_PRODUK_REF = "DetailPesananProduk"
    }

    fun getRef(): DatabaseReference {
        val fireabaseDB = FirebaseDatabase.getInstance()
        return fireabaseDB.getReference(refName)
    }
}