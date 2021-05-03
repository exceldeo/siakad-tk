package app.siakad.siakadtkadmin.presentation.screens.order.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import app.siakad.siakadtkadmin.presentation.screens.announcement.adapter.AnnouncementListAdater
import app.siakad.siakadtkadmin.presentation.screens.order.detail.adapter.OrderDetailAdapter

class OrderDetailActivity : AppCompatActivity() {

  private val pageTitle = "Detail Pesanan"

  private lateinit var tvName: TextView
  private lateinit var tvClass: TextView
  private lateinit var tvAddress: TextView
  private lateinit var tvHP: TextView
  private lateinit var tvOrderNum: TextView
  private lateinit var tvOrderTotal: TextView
  private lateinit var ivAccAll: ImageView
  private lateinit var ivRejectAll: ImageView
  private lateinit var rvOrderList: RecyclerView
  private lateinit var btnCancel: CardView
  private lateinit var btnSave: CardView

  private lateinit var orderListAdapter: OrderDetailAdapter

  private var pesanan: Pesanan? = null

  companion object {
    const val ORDER_DETAIL_ITEM = "order_detail_item"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order_detail)

    if (intent.getParcelableExtra<Pesanan>(ORDER_DETAIL_ITEM) != null) {
      pesanan = intent.getParcelableExtra<Pesanan>(ORDER_DETAIL_ITEM)
    }

    tvName = findViewById(R.id.tv_registration_detail_nama)
    tvClass = findViewById(R.id.tv_order_detail_kelas)
    tvAddress = findViewById(R.id.tv_order_detail_alamat)
    tvHP = findViewById(R.id.tv_order_detail_nohp)
    tvOrderNum = findViewById(R.id.tv_order_detail_jumlah)
    tvOrderTotal = findViewById(R.id.tv_order_detail_total)

    if (pesanan != null) {
      tvName.text = pesanan?.pengguna?.nama
      tvClass.text
      tvAddress.text = pesanan?.pengguna?.alamat
      tvHP.text = pesanan?.pengguna?.noHP
      tvOrderNum.text = pesanan?.pesanan?.detailPesanan?.size.toString()

      var totalHarga = 0
      pesanan?.pesanan?.detailPesanan?.forEach {
        totalHarga = it.harga
      }
      tvOrderTotal.text = totalHarga.toString()
    }

    setupAppBar()
    setupButtons()
    setupListAdapter()
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

  private fun setupButtons() {
    ivAccAll = findViewById(R.id.iv_order_detail_acc_all)
    ivRejectAll = findViewById(R.id.iv_order_detail_reject_all)
    btnCancel = findViewById(R.id.btn_order_detail_batal)
    btnSave = findViewById(R.id.btn_order_detail_simpan)
  }

  private fun setupAppBar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
    setSupportActionBar(toolbar)
    supportActionBar?.title = pageTitle
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun setupListAdapter() {
    rvOrderList = findViewById(R.id.rv_order_detail_daftar_pesanan)
    orderListAdapter = OrderDetailAdapter()
    rvOrderList.apply {
      setHasFixedSize(true)
      adapter = orderListAdapter
      layoutManager = LinearLayoutManager(this.context)
    }
  }
}