package app.siakad.siakadtk.infrastructure.viewmodels.registration

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.ModelContainer
import app.siakad.siakadtk.domain.ModelState
import app.siakad.siakadtk.domain.repositories.RegistrationRepository
import app.siakad.siakadtk.infrastructure.data.DaftarUlang
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationFormViewModel (private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val registrationRepository = RegistrationRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var insertObserver: Observer<ModelContainer<String>>

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

        registrationRepository.getInsertState().observe(lcOwner, insertObserver)
    }

    fun setData(namaSiswa: String, kelas: String, namaWali: String, gender: String, address: String, noHP: String, nominal: Int, fotoBayar: String) {
        vmCoroutineScope.launch {
            registrationRepository.insertData(
                DaftarUlang(
                    namaSiswa = namaSiswa,
                    kelas = kelas,
                    namaWali = namaWali,
                    jenisKelamin = gender,
                    alamat = address,
                    noHP = noHP,
                    nominalbayar = nominal,
                    fotoBayar = fotoBayar
                )
            )
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}