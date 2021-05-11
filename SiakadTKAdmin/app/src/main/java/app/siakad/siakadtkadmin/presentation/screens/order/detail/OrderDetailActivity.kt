package app.siakad.siakadtkadmin.presentation.screens.order.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.DetailKeranjangModel
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order.detail.OrderDetailViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.order.OrderListFragment
import app.siakad.siakadtkadmin.presentation.screens.order.detail.adapter.OrderDetailAdapter
import app.siakad.siakadtkadmin.presentation.screens.order.detail.helper.OrderDetailHelper
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox

class OrderDetailActivity : AppCompatActivity(), OrderDetailHelper, AlertListener {

  private val pageTitle = "Detail Pesanan"

  private lateinit var tvName: TextView
  private lateinit var tvClass: TextView
  private lateinit var tvAddress: TextView
  private lateinit var tvHP: TextView
  private lateinit var tvOrderNum: TextView
  private lateinit var tvOrderTotal: TextView
  private lateinit var cbAccAll: MaterialCheckBox
  private lateinit var rvOrderList: RecyclerView
  private lateinit var btnCancel: MaterialButton
  private lateinit var btnSave: MaterialButton

  private lateinit var orderListAdapter: OrderDetailAdapter
  private lateinit var vmOrderDetail: OrderDetailViewModel

  private var pesanan: Pesanan? = null
  private var orderType: String? = null
  private var detailPesananList: ArrayList<DetailKeranjangModel> = arrayListOf()
  private var detailPesananListIndex: ArrayList<Int> = arrayListOf()

  companion object {
    const val ORDER_DETAIL_ITEM = "order_detail_item"
    const val TAG_CONFIRM = "Terima Pesan"
    const val TAG_REJECT = "Tolak Pesan"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order_detail)

    if (intent.getParcelableExtra<Pesanan>(ORDER_DETAIL_ITEM) != null) {
      pesanan = intent.getParcelableExtra(ORDER_DETAIL_ITEM)
    }
    if (intent.getStringExtra(OrderListFragment.ORDER_TYPE) != null) {
      orderType = intent.getStringExtra(OrderListFragment.ORDER_TYPE)
      if (orderType == OrderListFragment.ORDER_DONE) {
        setContentView(R.layout.activity_order_detail_finish)
      }
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
    if (orderType != OrderListFragment.ORDER_DONE) {
      setupButtons()
    } else {
      cbAccAll = findViewById(R.id.cb_order_detail_acc_all)
      cbAccAll.visibility = View.GONE
    }
    setupViewModel()
    setupListAdapter()

    orderListAdapter.changeDataList(pesanan?.pesanan?.detailPesanan!!)
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
    cbAccAll = findViewById(R.id.cb_order_detail_acc_all)

    btnCancel = findViewById(R.id.btn_order_detail_tolak)
    btnCancel.setOnClickListener {
      val alertDialog = AlertDialogFragment(
        "Terima pesanan",
        "Apakah Anda yakin menerima pesanan ini?"
      )
      alertDialog.show(supportFragmentManager, TAG_REJECT)
    }

    btnSave = findViewById(R.id.btn_order_detail_simpan)
    btnSave.setOnClickListener {
      var alertDialog: AlertDialogFragment? = null

      if (orderType == OrderListFragment.ORDER_PENDING) {
        alertDialog = AlertDialogFragment(
          "Terima pesanan",
          "Apakah Anda yakin menerima pesanan ini?"
        )
      } else {
        alertDialog = AlertDialogFragment(
          "Selesaikan pesanan",
          "Apakah benar pesanan ini telah selesai?"
        )
      }

      alertDialog.show(supportFragmentManager, TAG_CONFIRM)
    }

    if (orderType != OrderListFragment.ORDER_PENDING) {
      cbAccAll.visibility = View.GONE
      btnCancel.visibility = View.GONE

      if (orderType == OrderListFragment.ORDER_PROCESS) {
        btnSave.text = "Pesanan Selesai"
      }
    }
  }

  private fun setupAppBar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
    setSupportActionBar(toolbar)
    supportActionBar?.title = pageTitle
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun setupListAdapter() {
    rvOrderList = findViewById(R.id.rv_order_detail_daftar_pesanan)
    orderListAdapter = OrderDetailAdapter(orderType!!)
    rvOrderList.apply {
      setHasFixedSize(true)
      adapter = orderListAdapter
      layoutManager = LinearLayoutManager(this.context)
    }
  }

  override fun checkTheItem(pos: Int) {
    detailPesananListIndex.add(pos)
  }

  override fun uncheckTheItem(pos: Int) {
    detailPesananListIndex.remove(pos)
  }

  private fun setupViewModel() {
    vmOrderDetail = ViewModelProvider(
      this,
      ViewModelFactory(this, this)
    ).get(OrderDetailViewModel::class.java)
  }

  override fun alertAction(tag: String?) {
    if (tag == TAG_CONFIRM) {
      if (orderType == OrderListFragment.ORDER_PENDING) {
        detailPesananListIndex.forEach {
          detailPesananList.add(pesanan?.pesanan?.detailPesanan!![it])
        }
        pesanan?.pesanan?.detailPesanan = detailPesananList
        vmOrderDetail.updateDataToAccepted(pesanan?.pesanan!!, OrderListFragment.ORDER_PROCESS)
      } else {
        vmOrderDetail.updateDataToAccepted(pesanan?.pesanan!!, OrderListFragment.ORDER_DONE)
      }
    } else if (tag == TAG_REJECT) {
      vmOrderDetail.removeData(pesanan?.pesanan!!)
      onBackPressed()
    }
  }
}