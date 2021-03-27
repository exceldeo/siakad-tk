package app.siakad.siakadtk.domain.repositories

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseRef(private val refName: String) {

    private val db: DatabaseReference = FirebaseDatabase.getInstance().getReference(refName)

    fun getKey(): String? {
        return db.push().key
    }

    fun getRef(): DatabaseReference {
        return db
    }
}