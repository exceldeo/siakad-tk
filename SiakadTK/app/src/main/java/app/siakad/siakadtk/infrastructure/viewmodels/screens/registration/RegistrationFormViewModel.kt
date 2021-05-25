package app.siakad.siakadtk.infrastructure.viewmodels.screens.registration

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.db.storage.FirebaseStrg
import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.models.KelasModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.ClassroomRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.repositories.RegistrationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.storage.WholeStorage
import app.siakad.siakadtk.domain.utils.listeners.classroom.ClassroomListListener
import app.siakad.siakadtk.domain.utils.listeners.classroom.ClassroomListener
import app.siakad.siakadtk.domain.utils.listeners.registration.UserListener
import app.siakad.siakadtk.domain.utils.listeners.storage.StorageListener
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import app.siakad.siakadtk.infrastructure.data.Pengguna
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationFormViewModel (private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), UserListener, StorageListener, ClassroomListener {
    private val registrationRepository = RegistrationRepository()
    private val userRepository = UserRepository()
    private val classroomRepository = ClassroomRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val fbStorage = WholeStorage(FirebaseStrg.USER_DETAIL_REF)

    private lateinit var insertObserver: Observer<ModelContainer<String>>
    private var daftarUlangUser = DaftarUlang()
    private val liveDataUser = MutableLiveData<Pengguna>()
    private val liveDataDaful = MutableLiveData<DaftarUlang>()
    private var dataUser = Pengguna()
    private val classroomLiveData = MutableLiveData<KelasModel>()
    private var dataKelas = KelasModel()

    init {
        registrationRepository.initEventListener(this)
        userRepository.getUserById(this)
    }

    fun setData(namaSiswa: String, kelas: String, namaWali: String, gender: String, bornDate: String, address: String, noHP: String, thnAjaran: String, nominal: Int, fotoBayar: Uri?) {
        daftarUlangUser = DaftarUlang(
            namaSiswa = namaSiswa,
            alamat = address,
            kelas = kelas,
            jenisKelamin = gender,
            tanggalLahir = bornDate,
            namaWali = namaWali,
            noHP = noHP,
            tahunAjaran = thnAjaran,
            nominalbayar = nominal,
            fotoBayar = ""
        )

        vmCoroutineScope.launch {
            fbStorage.uploadImage(
                this@RegistrationFormViewModel, fotoBayar!! ,
                System.currentTimeMillis().toString() + "." + getFileExtension(fotoBayar!!)
            )
        }
    }

    fun setKelasName(kelasId: String) {
        vmCoroutineScope.launch {
            classroomRepository.initGetClassroomListListenerById(this@RegistrationFormViewModel, kelasId)
        }
    }

    override fun setClassroomById(kelas: ModelContainer<KelasModel>) {
        if (kelas.status == ModelState.SUCCESS) {
            val item = kelas.data

            if (item != null) {
                dataKelas = KelasModel(
                    kelasId = item.kelasId,
                    namaKelas = item.namaKelas,
                    tahunMulai = item.tahunMulai,
                    tahunSelesai = item.tahunSelesai,
                    daftarSiswa = item.daftarSiswa
                )
                classroomLiveData.postValue(dataKelas)
            } else if (kelas.status == ModelState.ERROR) {
                showToast("Gagal mendapat kelas")
            }
        }
    }

    fun getClassroomListById() : LiveData<KelasModel> {
        return classroomLiveData
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun addDataDafulUser(user: ModelContainer<DaftarUlangModel>) {
        if (user.status == ModelState.SUCCESS) {
            val item = user.data

            if (item != null) {
                daftarUlangUser = DaftarUlang(
                    userId = AuthenticationRepository.fbAuth.currentUser?.uid.toString(),
                    dafulId = item.dafulId,
                    tanggalDaful = item.tanggal,
                    fotoBayar = item.fotoBayar,
                    statusDaful = item.statusDaful
                )
                liveDataDaful.postValue(daftarUlangUser)
            } else if (user.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_user))
            }
        }
    }

    override fun changeDataDafulUser(user: ModelContainer<DaftarUlangModel>) {
        if (user.status == ModelState.SUCCESS) {
            val item = user.data

            if (item != null) {
                daftarUlangUser = DaftarUlang(
                    userId = AuthenticationRepository.fbAuth.currentUser?.uid.toString(),
                    dafulId = item.dafulId,
                    tanggalDaful = item.tanggal,
                    fotoBayar = item.fotoBayar,
                    statusDaful = item.statusDaful
                )
                liveDataDaful.postValue(daftarUlangUser)
            } else if (user.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_user))
            }
        }
    }

    override fun removeDataDafulUser(user: ModelContainer<DaftarUlangModel>) {
        if (user.status == ModelState.SUCCESS) {
            daftarUlangUser = DaftarUlang()
            liveDataDaful.postValue(daftarUlangUser)
        }  else if (user.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_remove_daful))
        }
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

    fun getDaftarUlangData(): LiveData<DaftarUlang> {
        return liveDataDaful
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
            daftarUlangUser.fotoBayar = status.data!!
            if (daftarUlangUser.namaSiswa != "") {
                vmCoroutineScope.launch {
                    registrationRepository.insertData(
                        this@RegistrationFormViewModel, daftarUlangUser
                    )
                }
            } else {
                vmCoroutineScope.launch {
                    registrationRepository.updateData(
                        this@RegistrationFormViewModel, daftarUlangUser
                    )
                }
            }

            vmCoroutineScope.launch {
                userRepository.updateDetailData(
                    this@RegistrationFormViewModel, daftarUlangUser, dataUser
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
}