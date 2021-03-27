package app.siakad.siakadtkadmin.presentation.screens.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.product.adapter.ProductListAdapter
import app.siakad.siakadtkadmin.presentation.screens.product.utils.ProductType

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductListActivity : AppCompatActivity() {

    private var pageTitle = ""

    private lateinit var toolbar: Toolbar
    private lateinit var svProduct: SearchView
    private lateinit var tvNumProduct: TextView
    private lateinit var ivAddProduct: ImageView

    private lateinit var rvProduct: RecyclerView
    private lateinit var rvProductAdapter: ProductListAdapter

    companion object {
        const val PAGE_TYPE = "page type"
        const val BOOK_PAGE = "Buku"
        const val UNIFORM_PAGE = "Seragam"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        pageTitle = intent.getStringExtra(PAGE_TYPE)
        toolbar = findViewById(R.id.toolbar_main)
        svProduct = findViewById(R.id.sv_product_list_cari)
        tvNumProduct = findViewById(R.id.tv_product_list_jumlah_produk)
        ivAddProduct = findViewById(R.id.iv_product_list_tambah_produk)
        rvProduct = findViewById(R.id.rv_product_list_daftar_produk)

        rvProduct = findViewById(R.id.rv_product_list_daftar_produk)

        if (pageTitle == BOOK_PAGE) {
            rvProductAdapter = ProductListAdapter(ProductType.BUKU)
        } else {
            rvProductAdapter = ProductListAdapter(ProductType.SERAGAM)
        }
    }

    private fun setupView() {
        setupAppBar()
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}