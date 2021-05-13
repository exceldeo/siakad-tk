package app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserDetailRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.registration.UserListener
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import app.siakad.siakadtk.infrastructure.data.DetailPengguna
import app.siakad.siakadtk.infrastructure.data.Pengguna
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel (private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), UserListener {
    private val userDetailRepository = UserDetailRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var insertObserver: Observer<ModelContainer<String>>
    private val authRepository = AuthenticationRepository()
    private val liveDataUser = MutableLiveData<Pengguna>()
    private var dataUser = Pengguna()

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

    fun setData(namaSiswa: String, kelas: String, namaWali: String, thnAjaran: String,  gender: String, bornDate: String, address: String, noHP: String) {
        vmCoroutineScope.launch {
            userDetailRepository.insertData(
                DetailPengguna(
                    kelas = kelas,
                    tahunAjaran = thnAjaran,
                    jenisKelamin = gender,
                    tanggalLahir = bornDate,
                    namaOrtu = namaWali
                )
            )
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
}