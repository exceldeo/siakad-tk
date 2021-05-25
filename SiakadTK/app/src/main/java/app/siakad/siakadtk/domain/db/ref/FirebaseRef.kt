package app.siakad.siakadtk.domain.db.ref

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseRef(private val refName: String) {
    companion object {
        const val USER_REF = "Pengguna"
        const val USER_DETAIL_REF = "UserDetail"
        const val PENGUMUMAN_REF = "Pengumuman"
        const val DAFTAR_ULANG_REF = "DaftarUlang"
        const val PESANAN_REF = "Pesanan"
        const val PRODUK_REF = "Produk"
        const val FITUR_REF = "Fitur"
        const val SERAGAM_REF = "Seragam"
        const val BUKU_REF = "Buku"
        const val AKTIVITAS_REF = "Aktivitas"
        const val KERANJANG_REF = "Keranjang"
        const val DETAIL_PESANAN_PRODUK_REF = "DetailPesananProduk"
        const val KELAS_REF = "Kelas"
    }

    fun getRef(): DatabaseReference {
        val fireabaseDB = FirebaseDatabase.getInstance()
        return fireabaseDB.getReference(refName)
    }
}