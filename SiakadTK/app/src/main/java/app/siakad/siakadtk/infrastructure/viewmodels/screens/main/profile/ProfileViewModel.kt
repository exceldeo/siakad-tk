package app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.db.storage.FirebaseStrg
import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.models.DetailPenggunaModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserDetailRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.storage.WholeStorage
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.registration.UserListener
import app.siakad.siakadtk.domain.utils.listeners.setting.SettingListener
import app.siakad.siakadtk.domain.utils.listeners.storage.StorageListener
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import app.siakad.siakadtk.infrastructure.data.DetailPengguna
import app.siakad.siakadtk.infrastructure.data.Pengguna
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel (private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), UserListener, StorageListener, SettingListener {
    private val userDetailRepository = UserDetailRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val fbStorage = WholeStorage(FirebaseStrg.USER_DETAIL_REF)

    private lateinit var insertObserver: Observer<ModelContainer<String>>
    private val authRepository = AuthenticationRepository()
    private val liveDataUser = MutableLiveData<Pengguna>()
    private var dataUser = Pengguna()
    private var newPasswd = ""
    private var newEmail = ""
    private var dataDetailUser = DetailPenggunaModel()

    init {
        setupObserver()
    }

    private fun setupObserver() {
        insertObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                showToast(context.getString(R.string.scs_set_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_set_data))
            }
        }

        userDetailRepository.getInsertState().observe(lcOwner, insertObserver)
        userRepository.getUserById(this)
    }

    fun setData(namaSiswa: String, kelas: String, namaWali: String, thnAjaran: String,  gender: String, bornDate: String, address: String, noHP: String, fotoProfil: Uri?) {
        dataDetailUser = dataUser.detail!!
        dataDetailUser.namaOrtu = namaWali
        dataDetailUser.kelas = kelas
        dataDetailUser.tahunAjaran = thnAjaran
        dataDetailUser.tanggalLahir = bornDate
        dataDetailUser.jenisKelamin = gender

        dataUser.nama = namaSiswa
        dataUser.alamat = address
        dataUser.noHP = noHP
//        dataUser.detail = dataDetailUser

        vmCoroutineScope.launch {
            fbStorage.uploadImage(this@ProfileViewModel, fotoProfil!! ,
                System.currentTimeMillis().toString() + "." + getFileExtension(fotoProfil!!)
            )
        }
    }

    fun updatePassword(data: Pengguna, newPasswd: String) {
        this.newPasswd = newPasswd
        vmCoroutineScope.launch {
            authRepository.updatePassword(this@ProfileViewModel, data.email, data.passwd, newPasswd)
        }
    }

    fun logout() {
        authRepository.logout()
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun addDataDafulUser(user: ModelContainer<DaftarUlangModel>) {

    }

    override fun addDataUser(user: ModelContainer<PenggunaModel>) {
        if (user.status == ModelState.SUCCESS) {
            val item = user.data

            if (item != null) {
                dataUser = Pengguna(
                    nama = item.nama,
                    alamat = item.alamat,
                    noHP = item.noHP,
                    email = item.email,
                    passwd = item.passwd,
                    status = item.status,
                    detail = item.detailPengguna!!
                )
                liveDataUser.postValue(dataUser)
            } else if (user.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_user))
            }
        }
    }

    fun getUserData(): LiveData<Pengguna> {
        return liveDataUser
    }

    override fun notifyUserDetailChangeStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

    override fun notifyUploadStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            dataDetailUser.fotoSiswa = status.data!!
            dataUser.detail = dataDetailUser
            vmCoroutineScope.launch {
                userRepository.updateDataFromProfile(
                    this@ProfileViewModel, dataUser
                )
            }
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_upload_img))
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))
    }

    override fun notifyUserDetailPasswordStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            vmCoroutineScope.launch {
                dataUser.passwd = newPasswd
                userRepository.updateDataFromProfile(this@ProfileViewModel, dataUser)
            }
            showToast(context.getString(R.string.scs_set_passwd))
        } else {
            showToast(context.getString(R.string.fail_set_passwd))
        }
    }

    override fun notifyUserDetailEmailStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            vmCoroutineScope.launch {
                dataUser.email = newEmail
                userRepository.updateDataFromProfile(this@ProfileViewModel, dataUser)
            }
            showToast(context.getString(R.string.scs_set_email))
        } else {
            showToast(context.getString(R.string.fail_set_email))
        }
    }

    fun updateEmail(data: Pengguna, newEmail: String) {
        this.newEmail = newEmail
        vmCoroutineScope.launch {
            authRepository.updateEmail(this@ProfileViewModel, data.email, data.passwd, newEmail)
        }
    }
}