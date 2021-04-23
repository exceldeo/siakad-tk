package app.siakad.siakadtkadmin.domain.db.storage

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseStrg(private val strName: String) {
    companion object {
        const val USER_REF = "User"
        const val USER_DETAIL_REF = "UserDetail"
        const val DAFTAR_ULANG_REF = "DaftarUlang"
        const val PESANAN_REF = "Pesanan"
        const val PRODUK_REF = "Produk"
        const val SERAGAM_REF = "Seragam"
        const val BUKU_REF = "Buku"
    }

    fun getRef(): StorageReference {
        val firebaseStorage = FirebaseStorage.getInstance()
        return firebaseStorage.getReference(strName)
    }
}