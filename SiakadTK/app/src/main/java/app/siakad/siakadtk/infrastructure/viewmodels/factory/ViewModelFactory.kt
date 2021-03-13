package app.siakad.siakadtk.infrastructure.viewmodels.factory

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtk.infrastructure.viewmodels.announcement.AnnouncementViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.login.LoginViewModel

class ViewModelFactory(
    private val owner: LifecycleOwner,
    private val ctx: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AnnouncementViewModel::class.java) -> {
                AnnouncementViewModel(ctx, owner) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                AnnouncementViewModel(ctx, owner) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}