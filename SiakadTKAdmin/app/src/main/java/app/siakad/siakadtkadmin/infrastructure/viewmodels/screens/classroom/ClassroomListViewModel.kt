package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.repositories.ClassroomRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomListListener
import app.siakad.siakadtkadmin.infrastructure.data.Kelas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClassroomListViewModel(private val context: Context) :
    ViewModel(),
    ClassroomListListener {
    private val classroomListLiveData = MutableLiveData<ArrayList<KelasModel>>()
    private val classroomRepository = ClassroomRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val dataKelasList = arrayListOf<KelasModel>()

    fun setClassroomType(type: String) {
        if (dataKelasList.isEmpty()) {
            vmCoroutineScope.launch {
                classroomRepository.initGetClassroomListListener(this@ClassroomListViewModel, type)
            }
        }
    }

    override fun setClassroomList(kelasList: ModelContainer<ArrayList<KelasModel>>) {
        if (kelasList.status == ModelState.SUCCESS) {
            if (kelasList.data?.isNotEmpty()!!) {
                dataKelasList.addAll(kelasList.data!!)
                classroomListLiveData.postValue(dataKelasList)
                showToast(context.getString(R.string.scs_get_data))
            }
        } else if (kelasList.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_data))
        }
    }

    fun getClassroomList() : LiveData<ArrayList<KelasModel>> {
        return classroomListLiveData
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}