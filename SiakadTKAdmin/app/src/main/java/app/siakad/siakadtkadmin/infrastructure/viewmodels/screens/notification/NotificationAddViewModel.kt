package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.notification

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.repositories.NotificationRepository
import app.siakad.siakadtkadmin.domain.repositories.UserRepository
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import app.siakad.siakadtkadmin.infrastructure.data.Notifikasi
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotificationAddViewModel(private val context: Context, private val lcOwner: LifecycleOwner) :
    ViewModel(),
    UserListListener {
    private val userListLiveData = MutableLiveData<ArrayList<Siswa>>()
    private val notificationRepository = NotificationRepository()
    private val userRepository = UserRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var insertObserver: Observer<ModelContainer<String>>

    init {
        setupObserver()
        vmCoroutineScope.launch {
            userRepository.initGetUserListListener(this@NotificationAddViewModel)
        }
    }

    private fun setupObserver() {
        insertObserver = Observer { data ->
            if (data.status == ModelState.SUCCESS) {
                showToast(context.getString(R.string.scs_set_data))
            } else if (data.status == ModelState.ERROR) {
                showToast(context.getString(R.string.fail_set_data))
            }
        }

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

    override fun setUserList(penggunaList: ModelContainer<ArrayList<PenggunaModel>>) {
        if (penggunaList.status == ModelState.SUCCESS) {
            val siswaList = arrayListOf<Siswa>()
            if (penggunaList.data?.isNotEmpty()!!) {
                penggunaList.data?.forEach { user ->
                    siswaList.add(
                        Siswa(
                            nama = user.nama,
                            noHP = user.noHP,
                            email = user.email,
                            passwd = user.passwd,
                            alamat = user.alamat,
                            userId = user.userId,
                            status = user.status
                        )
                    )
                    userListLiveData.postValue(siswaList)
                }
                showToast(context.getString(R.string.scs_get_data))
            }
        } else if (penggunaList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    fun getUserList(): LiveData<ArrayList<Siswa>> {
        return userListLiveData
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}