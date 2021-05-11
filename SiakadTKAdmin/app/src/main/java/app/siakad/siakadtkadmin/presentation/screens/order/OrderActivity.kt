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
import androidx.viewpager.widget.ViewPager
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order.OrderListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.announcement.AnnouncementListFragment
import app.siakad.siakadtkadmin.presentation.screens.announcement.adapter.AnnouncementListAdater
import app.siakad.siakadtkadmin.presentation.screens.main.MainActivity
import app.siakad.siakadtkadmin.presentation.screens.order.adapter.OrderListAdapter
import app.siakad.siakadtkadmin.presentation.screens.order.detail.OrderDetailActivity
import app.siakad.siakadtkadmin.presentation.screens.order.helper.OrderClickHelper
import app.siakad.siakadtkadmin.presentation.utils.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class OrderActivity : AppCompatActivity(), OrderClickHelper {

  private val pageTitle = "Pesanan"

  private lateinit var pagerAdapter: ViewPagerAdapter
  private lateinit var viewPager: ViewPager
  private lateinit var tab: TabLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order)

    setupAppBar()
    setupTabLayout()
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

  private fun setupTabLayout() {
    pagerAdapter =
      ViewPagerAdapter(
        this,
        supportFragmentManager
      )
    pagerAdapter.addFragment(OrderListFragment.ORDER_PENDING, OrderListFragment.getPendingOrderListFragment())
    pagerAdapter.addFragment(OrderListFragment.ORDER_PROCESS, OrderListFragment.getProcessOrderListFragment())
    pagerAdapter.addFragment(OrderListFragment.ORDER_DONE, OrderListFragment.getDoneOrderListFragment())

    viewPager = findViewById(R.id.view_pager_order)
    viewPager.adapter = pagerAdapter

    tab = findViewById(R.id.tabs_order)
    tab.setupWithViewPager(viewPager)
  }

  override fun navigateToOrderDetail(pesanan: Pesanan) {
    val intent = Intent(this, OrderDetailActivity::class.java)
    intent.putExtra(OrderListFragment.ORDER_TYPE, pesanan.pesanan.statusPesan)
    intent.putExtra(OrderDetailActivity.ORDER_DETAIL_ITEM, pesanan)
    startActivity(intent)
  }
}