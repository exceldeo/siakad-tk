package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.repositories.AnnouncementRepository
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementListListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementListViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), AnnouncementListListener {
    private val announcementList = MutableLiveData<ArrayList<PengumumanModel>>()
    private val announcementRepository = AnnouncementRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val dataPengumumanList = arrayListOf<PengumumanModel>()

    fun setAnnouncementType(type: String) {
        if (dataPengumumanList.isEmpty()) {
            vmCoroutineScope.launch {
                announcementRepository.initGetAnnouncementListListener(
                    this@AnnouncementListViewModel,
                    type
                )
            }
        }
    }

    override fun setAnnouncementList(pengumumanList: ModelContainer<ArrayList<PengumumanModel>>) {
        if (pengumumanList.status == ModelState.SUCCESS) {
            if (pengumumanList.data?.isNotEmpty()!!) {
                dataPengumumanList.addAll(pengumumanList.data!!)
                announcementList.postValue(dataPengumumanList)
            }
        } else if (pengumumanList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    fun getAnnouncementList(): LiveData<ArrayList<PengumumanModel>> {
        return announcementList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}