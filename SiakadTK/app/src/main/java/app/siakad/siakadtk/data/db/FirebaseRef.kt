package app.siakad.siakadtk.data.db

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseRef<T>(private val refName: String, private val ctx: Context) {

    private val db: DatabaseReference = FirebaseDatabase.getInstance().getReference(refName)

    fun getKey(): String? {
        return db.push().key
    }

    fun getRef(): DatabaseReference {
        return db
    }
}