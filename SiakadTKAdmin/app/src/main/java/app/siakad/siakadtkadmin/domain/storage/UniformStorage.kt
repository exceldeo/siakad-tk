package app.siakad.siakadtkadmin.domain.storage

import android.net.Uri
import app.siakad.siakadtkadmin.domain.db.storage.FirebaseStrg
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask

class UniformStorage {
    private val uniformStrg = FirebaseStrg(FirebaseStrg.SERAGAM_REF).getRef()

    fun uploadImage(imageUri: Uri, fileName: String) {
        val filePath = uniformStrg.child(fileName)
        filePath.putFile(imageUri).continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let { exception ->
                    throw exception
                }
            }
            return@Continuation filePath.downloadUrl
        }).addOnCompleteListener { task ->
            val imageUrl = task.result.toString()
        }.addOnFailureListener { e ->
        }
    }
}