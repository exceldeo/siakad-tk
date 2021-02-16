package app.siakad.siakadtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView

class ProductListActivity : AppCompatActivity() {

    private lateinit var svProduct: SearchView
    private lateinit var rvSearchedProduct: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setupItemView()
    }

    private fun setupItemView() {
        svProduct = findViewById(R.id.sv_productlist_search_bar)
        rvSearchedProduct = findViewById(R.id.rv_productlist_searched_product_list)
    }
}