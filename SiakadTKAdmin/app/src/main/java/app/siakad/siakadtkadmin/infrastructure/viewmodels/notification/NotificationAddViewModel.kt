package app.siakad.siakadtkadmin.infrastructure.viewmodels.notification

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.ModelState
import app.siakad.siakadtkadmin.domain.models.UserModel
import app.siakad.siakadtkadmin.domain.repositories.NotificationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.infrastructure.data.Notifikasi
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotificationAddViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel() {
    private val userList = MutableLiveData<ArrayList<Siswa>>()
    private val notificationRepository = NotificationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var userObserver: Observer<ModelContainer<ArrayList<UserModel>>>
    private lateinit var insertObserver: Observer<ModelContainer<String>>

    init {
        setupObserver()
    }

    private fun setupObserver() {
        userObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                val users: ArrayList<UserModel> = data.data!!
                val siswaList = arrayListOf<Siswa>()

                users.forEach { item ->
                    siswaList.add(
                        Siswa(
                            userId = item.userId,
                            nama = item.nama,
                            alamat = item.alamat
                        )
                    )
                }
                userList.postValue(siswaList)
                showToast(context.getString(R.string.scs_get_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_get_data))
            }
        }

        insertObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                showToast(context.getString(R.string.scs_set_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_set_data))
            }
        }

        userRepository.getAllUser().observe(lcOwner, userObserver)
        notificationRepository.getInsertState().observe(lcOwner, insertObserver)
    }

    fun setData(title: String, content: String, date: String) {
        vmCoroutineScope.launch {
            notificationRepository.insertData(
                Notifikasi(
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