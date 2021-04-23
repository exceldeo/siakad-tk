package app.siakad.siakadtk.infrastructure.viewmodels.screens.register

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.db.storage.FirebaseStrg
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.models.PenggunaModel
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

    private var email: String = ""
    private var passwd: String = ""
    private var name: String = ""
    private var imageUri: Uri? = null
    private var detail = DetailPenggunaModel()
    private var pengguna = Pengguna()

    fun registerSiswa(email: String, passwd: String) {
        this.email = email
        this.passwd = passwd

        vmCoroutineScope.launch {
            authRepository.register(this@RegisterViewModel, email, passwd)
        }
    }

    fun insertPengguna(email: String, passwd: String, name: String, imageUri: Uri?) {
        this.name = name
        this.imageUri = imageUri

        pengguna = Pengguna(
            nama = name,
            email = email,
            passwd = passwd
        )

        if (imageUri != null) {
            vmCoroutineScope.launch {
                fbStorage.uploadImage(
                    this@RegisterViewModel, imageUri,
                    System.currentTimeMillis().toString() + "." + getFileExtension(imageUri)
                )
            }
        } else {
            vmCoroutineScope.launch {
                userRepository.insertData(this@RegisterViewModel, pengguna)
            }
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun notifyUploadStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            detail.fotoBayarAwal = status.data!!
            pengguna.detail = detail
            vmCoroutineScope.launch {
                userRepository.insertData(this@RegisterViewModel, pengguna)
            }
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_upload_img))
        }
    }

    override fun notifyRegisterStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_regis))
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_regis))
        }
    }

    override fun notifyDataInsertStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

}