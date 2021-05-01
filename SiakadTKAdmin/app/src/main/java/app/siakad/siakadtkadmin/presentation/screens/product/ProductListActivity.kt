package app.siakad.siakadtkadmin.presentation.screens.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.infrastructure.data.product.Buku
import app.siakad.siakadtkadmin.infrastructure.data.product.Seragam
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.ProductListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.product.adapter.ProductListAdapter
import app.siakad.siakadtkadmin.presentation.screens.product.book.BookAddActivity
import app.siakad.siakadtkadmin.presentation.screens.product.listener.ProductListListener
import app.siakad.siakadtkadmin.presentation.screens.product.uniform.UniformAddActivity
import app.siakad.siakadtkadmin.presentation.screens.product.utils.ProductType

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductListActivity : AppCompatActivity(), ProductListListener {

    private var pageTitle = ""

    private lateinit var svProduct: SearchView
    private lateinit var tvNumProduct: TextView
    private lateinit var ivAddProduct: ImageView

    private lateinit var rvProduct: RecyclerView
    private lateinit var rvProductAdapter: ProductListAdapter
    private lateinit var vmProductList: ProductListViewModel

    companion object {
        const val PAGE_TYPE = "page_type"
        const val BOOK_PAGE = "Buku"
        const val UNIFORM_PAGE = "Seragam"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        pageTitle = intent.getStringExtra(PAGE_TYPE)
        svProduct = findViewById(R.id.sv_product_list_cari)
        tvNumProduct = findViewById(R.id.tv_product_list_jumlah_produk)

        setupAppBar()
        setupViewModel()
        setupButtons()
        setupListAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    private fun setupButtons() {
        ivAddProduct = findViewById(R.id.iv_product_list_tambah_produk)
        ivAddProduct.setOnClickListener {
            if (pageTitle == UNIFORM_PAGE) {
                val intent = Intent(this@ProductListActivity, UniformAddActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@ProductListActivity, BookAddActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupListAdapter() {
        rvProduct = findViewById(R.id.rv_product_list_daftar_produk)
        if (pageTitle == BOOK_PAGE) {
            rvProductAdapter = ProductListAdapter(ProductType.BUKU)
        } else {
            rvProductAdapter = ProductListAdapter(ProductType.SERAGAM)
        }

        rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ProductListActivity)
            adapter = rvProductAdapter
        }
    }

    override fun navigateToUniformEdit(uniform: SeragamModel) {
        val intent = Intent(this@ProductListActivity, UniformAddActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToBookEdit(book: BukuModel) {
        val intent = Intent(this@ProductListActivity, BookAddActivity::class.java)
        intent.putExtra(BookAddActivity.BOOK_MODEL, book)
        startActivity(intent)
    }
}