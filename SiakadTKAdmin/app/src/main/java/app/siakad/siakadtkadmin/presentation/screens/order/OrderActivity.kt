package app.siakad.siakadtkadmin.presentation.screens.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order.OrderListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.announcement.adapter.AnnouncementListAdater
import app.siakad.siakadtkadmin.presentation.screens.main.MainActivity
import app.siakad.siakadtkadmin.presentation.screens.order.adapter.OrderListAdapter
import app.siakad.siakadtkadmin.presentation.screens.order.detail.OrderDetailActivity
import app.siakad.siakadtkadmin.presentation.screens.order.helper.OrderClickHelper

class OrderActivity : AppCompatActivity(), OrderClickHelper {

  private val pageTitle = "Pesanan"

  private lateinit var svOrder: SearchView
  private lateinit var tvNumOrder: TextView

  private lateinit var rvOrder: RecyclerView

  private lateinit var vmOrderList: OrderListViewModel
  private lateinit var orderListAdpater: OrderListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order)

    svOrder = findViewById(R.id.sv_order_cari)
    tvNumOrder = findViewById(R.id.tv_order_jumlah_pesananan)

    setupAppBar()
    setupListAdapter()
    setupViewModel()
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

  private fun setupListAdapter() {
    rvOrder = findViewById(R.id.rv_order_daftar_pesananan)
    orderListAdpater = OrderListAdapter()
    rvOrder.apply {
      setHasFixedSize(true)
      adapter = orderListAdpater
      layoutManager = LinearLayoutManager(this.context)
    }
  }

  private fun setupViewModel() {
    vmOrderList = ViewModelProvider(
      this, ViewModelFactory(this, this)
    ).get(OrderListViewModel::class.java)

    val obsOrderList = Observer<ArrayList<Pesanan>> { list ->
      if (list.size > 0) {
        orderListAdpater.changeDataList(list)
      }
    }

    vmOrderList.getOrderLIst()
      .observe(this, obsOrderList)
  }

  override fun navigateToOrderDetail(pesanan: Pesanan) {
    val intent = Intent(this, OrderDetailActivity::class.java)
    intent.putExtra(OrderDetailActivity.ORDER_DETAIL_ITEM, pesanan)
    startActivity(intent)
  }
}