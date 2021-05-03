package app.siakad.siakadtk.infrastructure.viewmodels.screens.registration

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DaftarUlangModel
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.repositories.RegistrationRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtk.domain.utils.listeners.registration.RegistrationListener
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationFormViewModel (private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), RegistrationListener {
    private val registrationRepository = RegistrationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var insertObserver: Observer<ModelContainer<String>>
    private val daftarUlangUser = DaftarUlang()
    init {
        setupObserver()
        registrationRepository.initEventListener(this)
    }

    private fun setupObserver() {
        insertObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                showToast(context.getString(R.string.scs_set_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_set_data))
            }
        }

        registrationRepository.getInsertState().observe(lcOwner, insertObserver)
    }

    fun setData(namaSiswa: String, kelas: String, namaWali: String, gender: String, bornDate: String, address: String, noHP: String, thnAjaran: String, nominal: Int, fotoBayar: String) {
        var daftarUlang = DaftarUlang(
            namaSiswa = namaSiswa,
            alamat = address,
            kelas = kelas,
            jenisKelamin = gender,
            tanggalLahir = bornDate,
            namaWali = namaWali,
            noHP = noHP,
            tahunAjaran = thnAjaran,
            nominalbayar = nominal,
            fotoBayar = fotoBayar
        )
        vmCoroutineScope.launch {
            registrationRepository.insertData(
                this@RegistrationFormViewModel, daftarUlang
            )
        }
        vmCoroutineScope.launch {
            userRepository.updateDetailData(
                this@RegistrationFormViewModel, daftarUlang
            )
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun addDataDafulUser(user: ModelContainer<DaftarUlangModel>) {
        if (user.status == ModelState.SUCCESS) {
            val item = user.data

            if (item != null) {
//                var daftarUlang = DaftarUlang(
//                    namaSiswa = namaSiswa,
//                    alamat = address,
//                    kelas = kelas,
//                    jenisKelamin = gender,
//                    tanggalLahir = bornDate,
//                    namaWali = namaWali,
//                    noHP = noHP,
//                    tahunAjaran = thnAjaran,
//                    nominalbayar = nominal,
//                    fotoBayar = fotoBayar
//                )
//                registrationRepository.insertData(
//                    this@RegistrationFormViewModel,
//                )
            } else if (user.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_user))
            }
        }
    }

    override fun notifyUserDetailChangeStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else {
            showToast(context.getString(R.string.fail_set_data))
        }
    }
}