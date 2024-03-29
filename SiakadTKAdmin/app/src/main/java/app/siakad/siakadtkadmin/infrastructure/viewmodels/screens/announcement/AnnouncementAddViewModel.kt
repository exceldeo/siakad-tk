package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.domain.models.PesananModel
import app.siakad.siakadtkadmin.domain.repositories.AnnouncementRepository
import app.siakad.siakadtkadmin.domain.repositories.ClassroomRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import app.siakad.siakadtkadmin.presentation.screens.announcement.AnnouncementListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AnnouncementAddViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
  ViewModel(), AnnouncementAddListener, ClassroomListListener {
  private val userListLiveData = MutableLiveData<ArrayList<PenggunaModel>>()
  private val classListLiveData = MutableLiveData<ArrayList<KelasModel>>()

  private val userLiveData = MutableLiveData<PenggunaModel>()
  private val classLiveData = MutableLiveData<KelasModel>()

  private val announcementRepository = AnnouncementRepository()
  private val userRepository = UserRepository()
  private val classroomRepository = ClassroomRepository()
  private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
  private val dataPenggunaList = arrayListOf<PenggunaModel>()
  private val dataKelasList = arrayListOf<KelasModel>()

  init {
    if (dataPenggunaList.isEmpty()) {
      vmCoroutineScope.launch {
        userRepository.initGetUserListListener(this@AnnouncementAddViewModel, true)
      }
    }
    if (dataKelasList.isEmpty()) {
      vmCoroutineScope.launch {
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)

        if (calendar.get(Calendar.MONTH) > 6) {
          year += 1
        }
        classroomRepository.initGetClassroomListByYearListener(
          this@AnnouncementAddViewModel,
          year
        )
      }
    }
  }

  override fun notifyAnnouncementAddStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_set_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_set_data))
    }
  }

  override fun notifyAnnouncementUpdateStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_update_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_update_data))
    }
  }

  override fun notifyAnnouncementDeleteStatus(status: ModelContainer<String>) {
    if (status.status == ModelState.SUCCESS) {
      showToast(context.getString(R.string.scs_del_data))
    } else if (status.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_del_data))
    }
  }

  override fun setUserById(pengguna: ModelContainer<PenggunaModel>) {
    if (pengguna.status == ModelState.SUCCESS) {
      userLiveData.postValue(pengguna.data!!)
      showToast(context.getString(R.string.scs_get_data))
    } else if (pengguna.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun setClassById(kelas: ModelContainer<KelasModel>) {
    if (kelas.status == ModelState.SUCCESS) {
      classLiveData.postValue(kelas.data!!)
      showToast(context.getString(R.string.scs_get_data))
    } else if (kelas.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun addUserItem(pengguna: ModelContainer<PenggunaModel>) {
    if (pengguna.status == ModelState.SUCCESS) {
      if (pengguna.data != null) {
        dataPenggunaList.add(pengguna.data!!)
        userListLiveData.postValue(dataPenggunaList)
        showToast(context.getString(R.string.scs_get_data))
      }
    } else if (pengguna.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  override fun setClassroomList(kelasList: ModelContainer<ArrayList<KelasModel>>) {
    if (kelasList.status == ModelState.SUCCESS) {
      if (kelasList.data?.isNotEmpty()!!) {
        dataKelasList.addAll(kelasList.data!!)
        classListLiveData.postValue(dataKelasList)
        showToast(context.getString(R.string.scs_get_data))
      }
    } else if (kelasList.status == ModelState.ERROR) {
      showToast(context.getString(R.string.fail_get_user))
    }
  }

  fun insertAnnouncement(
    title: String,
    content: String,
    date: String,
    type: String,
    target: String?,
    confirmable: Boolean = false
  ) {
    vmCoroutineScope.launch {
      var newTarget = ""
      if (target != null) {
        newTarget = target
      }

      announcementRepository.insertData(
        this@AnnouncementAddViewModel,
        PengumumanModel(
          tipe = type,
          judul = title,
          keterangan = content,
          tanggal = date,
          tujuanId = newTarget,
          confirmable = confirmable
        )
      )
    }
  }

  fun updateAnnouncement(
    title: String,
    content: String,
    date: String,
    type: String,
    target: String?,
    pengumuman: PengumumanModel,
    confirmable: Boolean = false
  ) {
    var newTarget = ""
    if (target != null) {
      newTarget = target
    }

    pengumuman.judul = title
    pengumuman.tanggal = date
    pengumuman.tipe = type
    pengumuman.keterangan = content
    pengumuman.confirmable = confirmable
    if (type == AnnouncementListFragment.TO_ALL) {
      pengumuman.tujuanId = ""
    } else {
      pengumuman.tujuanId = newTarget
    }

    vmCoroutineScope.launch {
      announcementRepository.updateData(
        this@AnnouncementAddViewModel,
        pengumuman
      )
    }
  }

  fun removeData(pengumuman: PengumumanModel) {
    vmCoroutineScope.launch {
      announcementRepository.removeAnnouncementData(this@AnnouncementAddViewModel, pengumuman)
    }
  }

  fun getClassroom(kelasId: String) {
    vmCoroutineScope.launch {
      classroomRepository.getClassById(this@AnnouncementAddViewModel, kelasId)
    }
  }

  fun getUser(userId: String) {
    vmCoroutineScope.launch {
      userRepository.getUserById(this@AnnouncementAddViewModel, userId)
    }
  }

  fun getUserList(): LiveData<ArrayList<PenggunaModel>> {
    return userListLiveData
  }

  fun getUserById(): LiveData<PenggunaModel> {
    return userLiveData
  }

  fun getClassroomList(): LiveData<ArrayList<KelasModel>> {
    return classListLiveData
  }

  fun getClassroomById(): LiveData<KelasModel> {
    return classLiveData
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}