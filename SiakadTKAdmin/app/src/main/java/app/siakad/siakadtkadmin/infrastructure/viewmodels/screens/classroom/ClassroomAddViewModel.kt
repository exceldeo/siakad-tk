package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.repositories.ClassroomRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.listeners.classroom.ClassroomAddListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClassroomAddViewModel(private val context: Context) :
    ViewModel(), ClassroomAddListener {
    private val classroomRepository = ClassroomRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun notifyClassroomAddStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

    fun insertClassroom(classroom: String, end: Int, start: Int) {
        vmCoroutineScope.launch {
            classroomRepository.insertData(
                this@ClassroomAddViewModel,
                KelasModel(
                    namaKelas = classroom,
                    tahunMulai = start,
                    tahunSelesai = end
                )
            )
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}