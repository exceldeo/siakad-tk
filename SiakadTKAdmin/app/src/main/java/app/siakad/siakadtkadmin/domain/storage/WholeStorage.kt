package app.siakad.siakadtkadmin.domain.storage

import android.net.Uri
import app.siakad.siakadtkadmin.domain.db.storage.FirebaseStrg
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.listeners.storage.StorageListener
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class WholeStorage(private val refName: String) {
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

    fun deleteImage(listener: StorageListener, imageUrl: String) {
        val filePath = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
        filePath.delete()
            .addOnCompleteListener { task ->
                listener.notifyDeleteStatus(ModelContainer.getSuccesModel("Success"))
            }.addOnFailureListener { e ->
                listener.notifyDeleteStatus(ModelContainer.getFailModel())
            }
    }
}