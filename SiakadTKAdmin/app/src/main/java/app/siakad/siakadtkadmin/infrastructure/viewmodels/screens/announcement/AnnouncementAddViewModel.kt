package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.ModelState
import app.siakad.siakadtkadmin.domain.repositories.AnnouncementRepository
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementAddViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val announcementRepository = AnnouncementRepository()
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

        announcementRepository.getInsertState().observe(lcOwner, insertObserver)
    }

    fun setData(title: String, content: String, date: String) {
        vmCoroutineScope.launch {
            announcementRepository.insertData(
                Pengumuman(
                    judul = title,
                    keterangan = content,
                    tanggal = date
                )
            )
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}