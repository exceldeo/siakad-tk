package app.siakad.siakadtk.presentation.main.product.detail.uniform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class ProductUniformListActivity : AppCompatActivity() {
    private val pageTitle = "List Seragam"

    private lateinit var toolbar: Toolbar
    private lateinit var svProduct: SearchView
    private lateinit var rvSearchedProduct: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_uniform_list)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        svProduct = findViewById(R.id.sv_product_unilist_search_bar)
        rvSearchedProduct = findViewById(R.id.rv_product_unilist_searched_product_list)
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