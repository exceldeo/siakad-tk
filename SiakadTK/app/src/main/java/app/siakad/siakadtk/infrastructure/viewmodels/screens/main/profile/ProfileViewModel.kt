package app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.repositories.AuthenticationRepository
import app.siakad.siakadtk.domain.repositories.UserDetailRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import app.siakad.siakadtk.infrastructure.data.DetailPengguna
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel (private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val userDetailRepository = UserDetailRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var insertObserver: Observer<ModelContainer<String>>
    private val authRepository = AuthenticationRepository()

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
    }

    fun setData(namaSiswa: String, kelas: String, namaWali: String, thnAjaran: String,  gender: String, bornDate: String, address: String, noHP: String) {
        vmCoroutineScope.launch {
            userDetailRepository.insertData(
                DetailPengguna(
                    nama = namaSiswa,
                    kelas = kelas,
                    thnAjaran = thnAjaran,
                    jenisKelamin = gender,
                    tanggalLahir = bornDate,
                    alamat = address,
                    noHP = noHP,
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
}