package app.siakad.siakadtkadmin.data.db

import android.content.Context
import android.widget.Toast
import app.siakad.siakadtkadmin.R
import com.google.firebase.database.DataSnapshot
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