package app.siakad.siakadtkadmin.presentation.announcement

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.data.repositories.AnnouncementRepository
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val announcementList = MutableLiveData<ArrayList<PengumumanModel>>()
    private val announcementRepository = AnnouncementRepository(context)
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var announcementRepoObserver: Observer<ArrayList<PengumumanModel>>

    init {
        vmCoroutineScope.launch {
            announcementRepository.initEventListener()
        }
        setupObserver()
    }

    private fun setupObserver() {
        announcementRepoObserver = Observer { list ->
            if (list.size > 0) {
                announcementList.postValue(list)
            }
        }

        announcementRepository.getAnnouncementList().observe(lcOwner, announcementRepoObserver)
    }

    fun getAnnouncementList(): LiveData<ArrayList<PengumumanModel>> {
        return announcementList
    }
}