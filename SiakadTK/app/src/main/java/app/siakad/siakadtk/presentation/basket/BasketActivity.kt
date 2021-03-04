package app.siakad.siakadtk.presentation.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class BasketActivity : AppCompatActivity() {
    private val pageTitle = "Keranjang Saya"

    private lateinit var toolbar: Toolbar
    private lateinit var rvBasket: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        setupItemWiew()
        setupView()
    }

    private fun setupItemWiew() {
        toolbar = findViewById(R.id.toolbar_main)
        rvBasket = findViewById(R.id.rv_basket_order_nota_list)
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