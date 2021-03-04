package app.siakad.siakadtk.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class ProductListActivity : AppCompatActivity() {
    private val pageTitle = "Jenis Produk"

    private lateinit var toolbar: Toolbar
    private lateinit var svProduct: SearchView
    private lateinit var rvSearchedProduct: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        svProduct = findViewById(R.id.sv_productlist_search_bar)
        rvSearchedProduct = findViewById(R.id.rv_productlist_searched_product_list)
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