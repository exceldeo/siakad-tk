package app.siakad.siakadtkadmin.presentation.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Pesanan
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.order.OrderListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.order.adapter.OrderListAdapter

class OrderListFragment(private val type: String) : Fragment() {
  private lateinit var tvOrderCount: TextView
  private lateinit var rvOrderList: RecyclerView

  private lateinit var vmOrderList: OrderListViewModel
  private lateinit var orderListAdapter: OrderListAdapter

  private lateinit var svOrder: SearchView

  companion object {
    const val ORDER_TYPE = "Order_Type"
    const val ORDER_PENDING = "Dipesan"
    const val ORDER_PROCESS = "Diproses"
    const val ORDER_DONE = "Selesai"

    fun getPendingOrderListFragment(): OrderListFragment {
      return OrderListFragment(
        ORDER_PENDING
      )
    }

    fun getProcessOrderListFragment(): OrderListFragment {
      return OrderListFragment(
        ORDER_PROCESS
      )
    }

    fun getDoneOrderListFragment(): OrderListFragment {
      return OrderListFragment(
        ORDER_DONE
      )
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_order_list, container, false)

    tvOrderCount = view.findViewById(R.id.tv_order_list_jumlah_pesanan)
    svOrder = view.findViewById(R.id.sv_order_list_cari)

    setupListAdapter(view)
    setupViewModel()

    return view
  }

  override fun onStart() {
    super.onStart()
    vmOrderList.setOrderType(type)
  }

  override fun onStop() {
    super.onStop()
    vmOrderList.clearListener()
  }

  private fun setupViewModel() {
    vmOrderList = ViewModelProvider(
      this, ViewModelFactory(this.viewLifecycleOwner, this.context)
    ).get(OrderListViewModel::class.java)

    val obsOrderList = Observer<ArrayList<Pesanan>> { newOrderList ->
      if (newOrderList.size > 0) {
        orderListAdapter.changeDataList(newOrderList)
      }
    }
    vmOrderList.getOrderLIst()
      .observe(this.viewLifecycleOwner, obsOrderList)
  }

  private fun setupListAdapter(v: View?) {
    if (v != null) {
      rvOrderList = v.findViewById(R.id.rv_order_list_daftar_pesanan)
      orderListAdapter = OrderListAdapter()
      rvOrderList.apply {
        setHasFixedSize(true)
        adapter = orderListAdapter
        layoutManager = LinearLayoutManager(this.context)
      }
    }
  }
}