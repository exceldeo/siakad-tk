package app.siakad.siakadtk.presentation.screens.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import app.siakad.siakadtk.infrastructure.viewmodels.screens.order.OrderViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.history.adapter.OrderHistoryAdapter
import app.siakad.siakadtk.presentation.screens.history.detail.HistoryDetailActivity
import app.siakad.siakadtk.presentation.screens.order.adapter.OrderAdapter
import app.siakad.siakadtk.presentation.screens.order.detail.OrderDetailActivity

class HistoryActivity : AppCompatActivity() {
    private val pageTitle = "Riwayat Pembayaran"

    private lateinit var toolbar: Toolbar
    private lateinit var rvHistory: RecyclerView

    private lateinit var vmOrderList: OrderViewModel
    private lateinit var rvHistoryAdapter: OrderHistoryAdapter
    private lateinit var orderListObserver: Observer<ArrayList<Pesanan>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setupItemWiew()
        setupView()
        setupObserver()
    }

    private fun setupItemWiew() {
        toolbar = findViewById(R.id.toolbar_main)
        rvHistory = findViewById(R.id.rv_history_order_list)

        rvHistory.setHasFixedSize(true)
        rvHistoryAdapter = OrderHistoryAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@HistoryActivity,
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

        rvHistoryAdapter.setOnItemClickCallback(object: OrderHistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Pesanan) {
                val intent = Intent(this@HistoryActivity, HistoryDetailActivity::class.java)
                intent.putExtra("pesanan_selesai", data);
                startActivity(intent)
            }
        })

        rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = rvHistoryAdapter
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
                var doneList = arrayListOf<Pesanan>()
                for (item in list)
                {
                    if(item.pesanan.statusPesan == OrderStateModel.ORDER_DONE.str)
                        doneList.add(item)
                }
                rvHistoryAdapter.changeDataList(doneList)
            }
        }

        vmOrderList.getOrderList().observe(this, orderListObserver)
    }
}