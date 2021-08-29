package app.siakad.siakadtk.presentation.screens.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.siakad.siakadtk.domain.models.product.BukuModel
import app.siakad.siakadtk.domain.models.product.SeragamModel
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.infrastructure.data.product.Seragam
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.product.ProductListViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.announcement.AnnouncementListActivity
import app.siakad.siakadtk.presentation.screens.main.product.detail.book.ProductBookDetailActivity
import app.siakad.siakadtk.presentation.screens.main.product.detail.uniform.ProductUniformDetailActivity
import app.siakad.siakadtk.presentation.screens.product.adapter.ProductListAdapter
import app.siakad.siakadtk.presentation.screens.product.utils.ProductType

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductListActivity : AppCompatActivity() {

    private var pageTitle = ""

    private lateinit var toolbar: Toolbar
    private lateinit var svProduct: SearchView
    private lateinit var rvProduct: RecyclerView
    private lateinit var rvProductAdapter: ProductListAdapter
    private lateinit var vmProductList: ProductListViewModel

    companion object {
        const val PAGE_TYPE = "page type"
        const val BOOK_PAGE = "Buku"
        const val UNIFORM_PAGE = "Seragam"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        pageTitle = intent.getStringExtra(PAGE_TYPE)

        setupAppBar()
        setupViewModel()
        setupItemView()
    }

    private fun setupItemView() {
        pageTitle = intent.getStringExtra(PAGE_TYPE)
        toolbar = findViewById(R.id.toolbar_main)
        svProduct = findViewById(R.id.sv_product_list_search_bar)
        rvProduct = findViewById(R.id.rv_product_list_product_list)

        if (pageTitle == BOOK_PAGE) {
            rvProductAdapter = ProductListAdapter(ProductType.BUKU)

            rvProductAdapter.setOnItemClickCallbackBook(object: ProductListAdapter.OnItemClickCallbackBook {
                override fun onItemClicked(data: BukuModel) {
                    showToast("Kamu memilih " + data.namaProduk)
                    val intent = Intent(this@ProductListActivity, ProductBookDetailActivity::class.java)
                    intent.putExtra("buku", data);
                    startActivity(intent)
                }
            })
        } else {
            rvProductAdapter = ProductListAdapter(ProductType.SERAGAM)

            rvProductAdapter.setOnItemClickCallbackUniform(object: ProductListAdapter.OnItemClickCallbackUniform {
                override fun onItemClicked(data: SeragamModel) {
                    showToast("Kamu memilih " + data.namaProduk)
                    val intent = Intent(this@ProductListActivity, ProductUniformDetailActivity::class.java)
                    intent.putExtra("seragam", data);
                    startActivity(intent)
                }
            })
        }

        rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ProductListActivity)
            adapter = rvProductAdapter
        }
    }

    private fun setupViewModel() {
        vmProductList = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(ProductListViewModel::class.java)
        vmProductList.setProductType(pageTitle)

        if (pageTitle == UNIFORM_PAGE) {
            val observer = Observer<ArrayList<SeragamModel>> { list ->
                if (list.size > 0) {
                    rvProductAdapter.changeUniformList(list)
                }
            }
            vmProductList.getUniformList().observe(this, observer)
        } else {
            val observer = Observer<ArrayList<BukuModel>> { list ->
                if (list.size > 0) {
                    rvProductAdapter.changeBookList(list)
                }
            }
            vmProductList.getBookList().observe(this, observer)
        }
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}