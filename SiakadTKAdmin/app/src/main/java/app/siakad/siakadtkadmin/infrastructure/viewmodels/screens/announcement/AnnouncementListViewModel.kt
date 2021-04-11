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
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementListViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(), AnnouncementListListener {
    private val announcementList = MutableLiveData<ArrayList<Pengumuman>>()
    private val announcementRepository = AnnouncementRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    fun setAnnouncementType(type: String) {
        vmCoroutineScope.launch {
            announcementRepository.initGetAnnouncementListListener(
                this@AnnouncementListViewModel,
                type
            )
        }
    }

    override fun setAnnouncementList(pengumumanList: ModelContainer<ArrayList<PengumumanModel>>) {
        if (pengumumanList.status == ModelState.SUCCESS) {
            val dataPengumumanList = arrayListOf<Pengumuman>()
            if (pengumumanList.data?.isNotEmpty()!!) {
                pengumumanList.data?.forEach { item ->
                    dataPengumumanList.add(
                        Pengumuman(
                            pengumumanId = item.pengumumanId,
                            judul = item.judul,
                            keterangan = item.keterangan,
                            tanggal = item.tanggal
                        )
                    )
                    announcementList.postValue(dataPengumumanList)
                }
                showToast(context.getString(R.string.scs_get_data))
            }
        } else if (pengumumanList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    fun getAnnouncementList(): LiveData<ArrayList<Pengumuman>> {
        return announcementList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}