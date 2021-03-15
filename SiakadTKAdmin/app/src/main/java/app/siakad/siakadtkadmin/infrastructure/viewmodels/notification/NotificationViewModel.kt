package app.siakad.siakadtkadmin.infrastructure.viewmodels.notification

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.ModelState
import app.siakad.siakadtkadmin.domain.models.NotifikasiModel
import app.siakad.siakadtkadmin.domain.repositories.NotificationRepository
import app.siakad.siakadtkadmin.infrastructure.data.Notifikasi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotificationViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val notificationList = MutableLiveData<ArrayList<Notifikasi>>()
    private val notificationRepository = NotificationRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var notificationRepoObserver: Observer<ModelContainer<ArrayList<NotifikasiModel>>>

    init {
        vmCoroutineScope.launch {
            notificationRepository.initEventListener()
        }
        setupObserver()
    }

    private fun setupObserver() {
        notificationRepoObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                val dataRepo = arrayListOf<Notifikasi>()
                val list = data.data

                list?.forEach { item ->
                    dataRepo.add(
                        Notifikasi(
                            notifikasiId = item.notifikasiId,
                            judul = item.judul,
                            keterangan = item.keterangan,
                            tanggal = item.tanggal
                        )
                    )
                }
                notificationList.postValue(dataRepo)
                showToast(context.getString(R.string.scs_get_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_data))
            }
        }

        notificationRepository.getNotificationList().observe(lcOwner, notificationRepoObserver)
    }

    fun getNotificationList(): LiveData<ArrayList<Notifikasi>> {
        return notificationList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}