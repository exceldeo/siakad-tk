package app.siakad.siakadtk.infrastructure.viewmodels.utils.factory

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtk.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.login.LoginViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.product.ProductListViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile.ProfileViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.order.OrderViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.register.RegisterViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.registration.RegistrationFormViewModel

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
                LoginViewModel(ctx, owner) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(ctx, owner) as T
            }
            modelClass.isAssignableFrom(RegistrationFormViewModel::class.java) -> {
                RegistrationFormViewModel(ctx, owner) as T
            }
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(ctx, owner) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(ctx, owner) as T
            }
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                ProductListViewModel(ctx, owner) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}