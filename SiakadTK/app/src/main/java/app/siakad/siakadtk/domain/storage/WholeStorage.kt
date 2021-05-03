package app.siakad.siakadtk.domain.storage

import android.net.Uri
import app.siakad.siakadtk.domain.db.storage.FirebaseStrg
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.listeners.storage.StorageListener
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask

class WholeStorage(refName: String) {
    private val storage = FirebaseStrg(refName).getRef()

    fun uploadImage(listener: StorageListener, imageUri: Uri, fileName: String) {
        val filePath = storage.child(fileName)
        filePath.putFile(imageUri)
            .continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { exception ->
                        throw exception
                    }
                }
                return@Continuation filePath.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val imageUrl = task.result.toString()
                    listener.notifyUploadStatus(ModelContainer.getSuccesModel(imageUrl))
                } else {
                    listener.notifyUploadStatus(ModelContainer.getFailModel())
                }
            }.addOnFailureListener { e ->
                listener.notifyUploadStatus(ModelContainer.getFailModel())
            }
    }
}