package app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.login.LoginViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.notification.NotificationAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.notification.NotificationViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.register.RegisterViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.UserListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.detail.UserDetailViewModel

class ViewModelFactory(
    private val owner: LifecycleOwner,
    private val ctx: Context?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AnnouncementViewModel::class.java) -> {
                AnnouncementViewModel(
                    ctx!!,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(AnnouncementAddViewModel::class.java) -> {
                AnnouncementAddViewModel(
                    ctx!!,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> {
                NotificationViewModel(
                    ctx!!,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(NotificationAddViewModel::class.java) -> {
                NotificationAddViewModel(
                    ctx!!,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(
                    ctx!!,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(
                    ctx!!,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(UserListViewModel::class.java) -> {
                UserListViewModel(
                    ctx!!
                ) as T
            }
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> {
                UserDetailViewModel(
                    ctx!!
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}