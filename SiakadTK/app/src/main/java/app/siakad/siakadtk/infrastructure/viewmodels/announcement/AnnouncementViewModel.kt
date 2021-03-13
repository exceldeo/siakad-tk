package app.siakad.siakadtk.infrastructure.viewmodels.announcement

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.ModelContainer
import app.siakad.siakadtk.domain.ModelState
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.repositories.AnnouncementRepository
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val announcementList = MutableLiveData<ArrayList<Pengumuman>>()
    private val announcementRepository = AnnouncementRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var announcementRepoObserver: Observer<ModelContainer<ArrayList<PengumumanModel>>>

    init {
        vmCoroutineScope.launch {
            announcementRepository.initEventListener()
        }
        setupObserver()
    }

    private fun setupObserver() {
        announcementRepoObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                val dataRepo = arrayListOf<Pengumuman>()
                val list = data.data

                list?.forEach{ item ->
                    dataRepo.add(
                        Pengumuman(
                            pengumumanId = item.pengumumanId,
                            judul = item.judul,
                            keterangan = item.keterangan,
                            tanggal = item.tanggal
                        )
                    )
                }
                announcementList.postValue(dataRepo)
                showToast(context.getString(R.string.scs_get_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_data))
            }
        }

        announcementRepository.getAnnouncementList().observe(lcOwner, announcementRepoObserver)
    }

    fun getAnnouncementList(): LiveData<ArrayList<Pengumuman>> {
        return announcementList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}