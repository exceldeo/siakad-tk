package app.siakad.siakadtk.domain.db.storage

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseStrg(private val strName: String) {
    companion object {
        const val USER_REF = "User"
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
    }

    fun getRef(): StorageReference {
        val firebaseStorage = FirebaseStorage.getInstance()
        return firebaseStorage.getReference(strName)
    }
}