package app.siakad.siakadtk.presentation.screens.main.product.basket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.infrastructure.data.DetailKeranjang
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.basket.KeranjangViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.announcement.inside.adapter.AnnouncementInsideAdapter
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.presentation.screens.product.basket.adapter.BasketAdapter

class BasketActivity : AppCompatActivity() {
    private val pageTitle = "Keranjang Saya"

    private lateinit var toolbar: Toolbar
    private lateinit var rvBasket: RecyclerView
    private lateinit var rvBasketAdapter: BasketAdapter

    private lateinit var vmBasket: KeranjangViewModel
    private lateinit var basketListObserver: Observer<ArrayList<DetailKeranjang>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        setupItemWiew()
        setupAppBar()
        setupView()
        setupObserver()
    }

    private fun setupItemWiew() {
        toolbar = findViewById(R.id.toolbar_main)
        rvBasket = findViewById(R.id.rv_basket_order_nota_list)

        rvBasket.setHasFixedSize(true)
        rvBasketAdapter = BasketAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@BasketActivity,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        setupAppBar()

        rvBasket.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@BasketActivity)
            adapter = rvBasketAdapter
        }

        vmBasket = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(KeranjangViewModel::class.java)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObserver() {
        basketListObserver = Observer { list ->
            if (list.size > 0) {
                rvBasketAdapter.changeDataList(list)
            }
        }

        vmBasket.getBasketList().observe(this, basketListObserver)
    }
}