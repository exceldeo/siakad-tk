package app.siakad.siakadtk.presentation.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.presentation.product.adapter.ProductListAdapter
import app.siakad.siakadtk.presentation.product.utils.ProductType

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductListActivity : AppCompatActivity() {

    private var pageTitle = ""

    private lateinit var toolbar: Toolbar
    private lateinit var svProduct: SearchView
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
        svProduct = findViewById(R.id.sv_product_list_search_bar)
        rvProduct = findViewById(R.id.rv_product_list_product_list)

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