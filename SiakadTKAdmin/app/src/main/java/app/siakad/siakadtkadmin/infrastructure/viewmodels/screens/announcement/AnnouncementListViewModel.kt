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

  override fun addAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>) {
    if (pengumuman.status == ModelState.SUCCESS) {
      if (pengumuman.data != null) {
        dataPengumumanList.add(pengumuman.data!!)
        announcementList.postValue(dataPengumumanList)
      }
    } else if (pengumuman.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun updateAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>) {
    if (pengumuman.status == ModelState.SUCCESS) {
      if (pengumuman.data != null) {
        dataPengumumanList.forEachIndexed { index, item ->
          if (item.tujuanId == pengumuman.data?.tujuanId) {
            dataPengumumanList[index] = pengumuman.data!!
          }
        }
        announcementList.postValue(dataPengumumanList)
      }
    } else if (pengumuman.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_update_data))
    }
  }

  override fun removeAnnouncementItem(pengumuman: ModelContainer<PengumumanModel>) {
    if (pengumuman.status == ModelState.SUCCESS) {
      if (pengumuman.data != null) {
        var target = 0
        dataPengumumanList.forEachIndexed feData@{ index, item ->
          if (item.tujuanId == pengumuman.data?.tujuanId) {
            target = index
            return@feData
          }
        }
        dataPengumumanList.removeAt(target)
        announcementList.postValue(dataPengumumanList)
      }
    } else if (pengumuman.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_update_data))
    }
  }

  fun getAnnouncementList(): LiveData<ArrayList<PengumumanModel>> {
    return announcementList
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}