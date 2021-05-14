package app.siakad.siakadtk.presentation.screens.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import app.siakad.siakadtk.infrastructure.data.product.Seragam
import app.siakad.siakadtk.infrastructure.viewmodels.screens.basket.KeranjangViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.order.OrderViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.presentation.screens.main.product.detail.uniform.ProductUniformDetailActivity
import app.siakad.siakadtk.presentation.screens.order.adapter.OrderAdapter
import app.siakad.siakadtk.presentation.screens.order.detail.OrderDetailActivity
import app.siakad.siakadtk.presentation.screens.product.adapter.ProductListAdapter

class OrderListActivity : AppCompatActivity() {
    private val pageTitle = "Pemesanan"

    private lateinit var rvOrderList: RecyclerView

    private lateinit var vmOrderList: OrderViewModel
    private lateinit var rvOrderAdapter: OrderAdapter
    private lateinit var orderListObserver: Observer<ArrayList<Pesanan>>

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        setupItemView()
        setupAppBar()
        setupView()
        setupObserver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@OrderListActivity,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        rvOrderList = findViewById(R.id.rv_aorderlist_order_list)

        rvOrderList.setHasFixedSize(true)
        rvOrderAdapter = OrderAdapter()
    }

    private fun setupView() {
        setupAppBar()

        rvOrderAdapter.setOnItemClickCallback(object: OrderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Pesanan) {
                val intent = Intent(this@OrderListActivity, OrderDetailActivity::class.java)
                intent.putExtra("pesanan", data);
                startActivity(intent)
            }
        })

        rvOrderList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@OrderListActivity)
            adapter = rvOrderAdapter
        }

        vmOrderList = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(OrderViewModel::class.java)

    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObserver() {
        orderListObserver = Observer { list ->
            if (list.size > 0) {
                rvOrderAdapter.changeDataList(list)
            }
        }

        vmOrderList.getOrderList().observe(this, orderListObserver)
    }
}