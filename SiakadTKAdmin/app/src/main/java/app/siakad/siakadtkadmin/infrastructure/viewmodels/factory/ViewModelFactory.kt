package app.siakad.siakadtkadmin.infrastructure.viewmodels.factory

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.infrastructure.viewmodels.announcement.AnnouncementViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.announcement.AnnouncementAddViewModel

class ViewModelFactory(
    private val owner: LifecycleOwner,
    private val ctx: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AnnouncementViewModel::class.java) -> {
                AnnouncementViewModel(
                    ctx,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(AnnouncementAddViewModel::class.java) -> {
                AnnouncementAddViewModel(
                    ctx
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}