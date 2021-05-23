package app.siakad.siakadtk.infrastructure.viewmodels.screens.register

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.db.storage.FirebaseStrg
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.storage.WholeStorage
import app.siakad.siakadtk.domain.utils.listeners.register.RegisterListener
import app.siakad.siakadtk.domain.utils.listeners.storage.StorageListener
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.presentation.utils.listener.AuthenticationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(private val context: Context, private val lcOwner: LifecycleOwner) : ViewModel(), RegisterListener, StorageListener {
    private val authRepository = AuthenticationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val fbStorage = WholeStorage(FirebaseStrg.USER_DETAIL_REF)

    private var detailPengguna = DetailPenggunaModel()
    private var pengguna = Pengguna()
    private lateinit var thisImageUri: Uri

    fun registerSiswa(email: String, passwd: String, name: String, imageUri: Uri?) {
        pengguna = Pengguna(
            nama = name,
            email = email,
            passwd = passwd,
            status = false
        )
        thisImageUri = imageUri!!
        vmCoroutineScope.launch {
            authRepository.register(this@RegisterViewModel, pengguna.email, pengguna.passwd)
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))
    }

    override fun notifyUploadStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            detailPengguna.fotoBayarAwal = status.data!!
            pengguna.detail = detailPengguna

            AuthenticationRepository.setUser(pengguna.userId, pengguna.email, pengguna.passwd, pengguna.status)

            if(pengguna.status) (context as AuthenticationListener).navigateToMain()
            else (context as AuthenticationListener).navigateToPendingMain()

            vmCoroutineScope.launch {
                userRepository.insertData(this@RegisterViewModel, pengguna)
            }
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_upload_img))
        }
    }

    override fun notifyDataInsertStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

    override fun notifyRegisterStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_regis))
            vmCoroutineScope.launch {
                fbStorage.uploadImage(
                    this@RegisterViewModel, thisImageUri,
                    System.currentTimeMillis().toString() + "." + getFileExtension(thisImageUri)
                )
            }
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_regis))
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}