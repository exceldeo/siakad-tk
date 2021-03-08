package app.siakad.siakadtkadmin.infrastructure.viewmodels.factory

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.infrastructure.viewmodels.announcement.AnnouncementViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.announcement.AnnouncementAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.login.LoginViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.main.setting.SettingViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.register.RegisterViewModel

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
                    ctx,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(
                    ctx,
                    owner
                ) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(
                    ctx,
                    owner
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}