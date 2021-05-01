package app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory

import android.content.Context
import androidx.lifecycle.*
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.ClassroomAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.ClassroomListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.detail.ClassroomDetailViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.login.LoginViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.ProductListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.book.BookAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.uniform.UniformAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.register.RegisterViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.registration.RegistrationListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.UserListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.detail.UserDetailViewModel

class ViewModelFactory(
    private val owner: LifecycleOwner,
    private val ctx: Context?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AnnouncementListViewModel::class.java) -> {
                AnnouncementListViewModel(
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
            modelClass.isAssignableFrom(RegistrationListViewModel::class.java) -> {
                RegistrationListViewModel(
                    ctx!!
                ) as T
            }
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                ProductListViewModel(
                    ctx!!
                ) as T
            }
            modelClass.isAssignableFrom(UniformAddViewModel::class.java) -> {
                UniformAddViewModel(
                    ctx!!
                ) as T
            }
            modelClass.isAssignableFrom(BookAddViewModel::class.java) -> {
                BookAddViewModel(
                    ctx!!
                ) as T
            }
            modelClass.isAssignableFrom(ClassroomListViewModel::class.java) -> {
                ClassroomListViewModel(
                    ctx!!
                ) as T
            }
            modelClass.isAssignableFrom(ClassroomAddViewModel::class.java) -> {
                ClassroomAddViewModel(
                    ctx!!
                ) as T
            }
            modelClass.isAssignableFrom(ClassroomDetailViewModel::class.java) -> {
                ClassroomDetailViewModel(
                    ctx!!
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}