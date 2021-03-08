package app.siakad.siakadtkadmin.presentation.utils.factory

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtk.presentation.announcement.AnnouncementViewModel

class ViewModelFactory(
    private val owner: LifecycleOwner,
    private val ctx: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AnnouncementViewModel::class.java) -> {
                AnnouncementViewModel(ctx, owner) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}