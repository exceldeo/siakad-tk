package app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.repositories.ProductRepository
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.utils.listeners.product.ProductListListener
import app.siakad.siakadtkadmin.presentation.screens.product.ProductListActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductListViewModel(private val context: Context) :
    ViewModel(), ProductListListener {
    private val uniformList = MutableLiveData<ArrayList<SeragamModel>>()
    private val bookList = MutableLiveData<ArrayList<BukuModel>>()

    private val productRepository = ProductRepository()
    private val vmCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private val dataSeragamList = arrayListOf<SeragamModel>()
    private val dataBukuList = arrayListOf<BukuModel>()

    fun setProductType(type: String) {
        if (type == ProductListActivity.UNIFORM_PAGE && dataSeragamList.isEmpty()) {
            vmCoroutineScope.launch {
                productRepository.initGetUniformEventListener(
                    this@ProductListViewModel
                )
            }
        } else if (type == ProductListActivity.BOOK_PAGE && dataBukuList.isEmpty()) {
            vmCoroutineScope.launch {
                productRepository.initGetBookEventListener(
                    this@ProductListViewModel
                )
            }
        }
    }

    override fun setUniformList(product: ModelContainer<ArrayList<SeragamModel>>) {
        if (product.status == ModelState.SUCCESS) {
            if (product.data?.isNotEmpty()!!) {
                dataSeragamList.clear()
                dataSeragamList.addAll(product.data!!)
                uniformList.postValue(dataSeragamList)
            }
        } else if (product.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    override fun setBookList(product: ModelContainer<ArrayList<BukuModel>>) {
        if (product.status == ModelState.SUCCESS) {
            if (product.data?.isNotEmpty()!!) {
                dataBukuList.clear()
                    dataBukuList.addAll(product.data!!)
                    bookList.postValue(dataBukuList)
            }
        } else if (product.status == ModelState.ERROR) {
            showToast(context.getString(R.string.fail_get_user))
        }
    }

    fun getUniformList(): LiveData<ArrayList<SeragamModel>> {
        return uniformList
    }

    fun getBookList(): LiveData<ArrayList<BukuModel>> {
        return bookList
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}