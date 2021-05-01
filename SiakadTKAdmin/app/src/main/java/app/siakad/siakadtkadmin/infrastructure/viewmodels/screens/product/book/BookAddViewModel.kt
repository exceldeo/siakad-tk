package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.book

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.db.storage.FirebaseStrg
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.repositories.ProductRepository
import app.siakad.siakadtkadmin.domain.storage.WholeStorage
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListener
import app.siakad.siakadtkadmin.domain.utils.listeners.storage.StorageListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BookAddViewModel(private val context: Context) :
    ViewModel(), ProductListener, StorageListener {
    private val productRepository = ProductRepository()
    private val fbStorage = WholeStorage(FirebaseStrg.BUKU_REF)
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var buku = BukuModel()
    private var updateImageUri: Uri? = null
    private var isUpdateData: Boolean = false

    override fun notifyInsertDataStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            showToast(context.getString(R.string.scs_set_data))
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_set_data))
        }
    }

    override fun notifyUploadStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            buku.fotoProduk = status.data!!
            if (isUpdateData) {
                vmCoroutineScope.launch {
                    productRepository.updateDataBuku(this@BookAddViewModel, buku)
                }
            } else {
                vmCoroutineScope.launch {
                    productRepository.insertDataBuku(this@BookAddViewModel, buku)
                }
            }
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_upoad_img))
        }
    }

    override fun notifyDeleteStatus(status: ModelContainer<String>) {
        if (status.status == ModelState.SUCCESS) {
            fbStorage.uploadImage(
                this@BookAddViewModel, updateImageUri!!,
                System.currentTimeMillis().toString() + "." + getFileExtension(updateImageUri!!)
            )
        } else if (status.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_upoad_img))
        }
    }

    fun insertBook(
        name: String,
        imageUri: Uri?,
        jumlah: Int,
        harga: Int
    ) {
        buku = BukuModel(
            namaProduk = name,
            harga = harga,
            jumlah = jumlah
        )

        if (imageUri != null) {
            vmCoroutineScope.launch {
                fbStorage.uploadImage(
                    this@BookAddViewModel, imageUri,
                    System.currentTimeMillis().toString() + "." + getFileExtension(imageUri)
                )
            }
        } else {
            vmCoroutineScope.launch {
                productRepository.insertDataBuku(this@BookAddViewModel, buku)
            }
        }
    }

    fun updateBook(
        name: String,
        imageUri: Uri?,
        jumlah: Int,
        harga: Int,
        data: BukuModel
    ) {
        data.jumlah = jumlah
        data.harga = harga
        data.namaProduk = name
        buku = data

        isUpdateData = true

        if (imageUri != null) {
            updateImageUri = imageUri
            vmCoroutineScope.launch {
                fbStorage.deleteImage(
                    this@BookAddViewModel, data.fotoProduk
                )
            }
        } else {
            vmCoroutineScope.launch {
                productRepository.updateDataBuku(this@BookAddViewModel, buku)
            }
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}