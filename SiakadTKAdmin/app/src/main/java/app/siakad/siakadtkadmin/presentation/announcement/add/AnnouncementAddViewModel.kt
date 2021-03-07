package app.siakad.siakadtkadmin.presentation.announcement.add

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.data.repositories.AnnouncementRepository
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementAddViewModel(private val context: Context) :
    ViewModel() {
    private val announcementRepository = AnnouncementRepository(context)
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    fun setData(title: String, content: String, date: String) {
        vmCoroutineScope.launch {
            announcementRepository.insertData(
                PengumumanModel(
                    judul = title,
                    keterangan = content,
                    tanggal = date
                )
            )
        }
    }
}