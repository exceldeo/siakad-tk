package app.siakad.siakadtk.presentation.screens.main.product.basket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.utils.listeners.basket.BasketAddListener
import app.siakad.siakadtk.infrastructure.viewmodels.screens.basket.KeranjangViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.presentation.screens.product.basket.adapter.BasketAdapter
import app.siakad.siakadtk.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtk.presentation.views.alert.AlertListener

class BasketActivity : AppCompatActivity(), BasketAddListener, AlertListener {
    private val pageTitle = "Keranjang Saya"

    private lateinit var toolbar: Toolbar
    private lateinit var rvBasket: RecyclerView
    private lateinit var btnOrder: TextView
//    private lateinit var cbAllCheckBox: CheckBox
    private lateinit var tvTotalPaymentChecked: TextView
    private lateinit var rvBasketAdapter: BasketAdapter

    private var checkedItems = mutableSetOf<Int>()
    private var newDetailKeranjang = arrayListOf<DetailKeranjangModel>()
    private var detailKeranjangs = arrayListOf<DetailKeranjangModel>()

    private lateinit var vmBasket: KeranjangViewModel
    private lateinit var basketListObserver: Observer<ArrayList<DetailKeranjangModel>>

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
        btnOrder = findViewById(R.id.btn_basket_order)
//        cbAllCheckBox = findViewById(R.id.cb_basket_select_all)
        tvTotalPaymentChecked = findViewById(R.id.tv_basket_total_payment)

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

    @SuppressLint("SetTextI18n")
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

        btnOrder.setOnClickListener {
            var totalPayment = 0
            for (item in checkedItems) {
                newDetailKeranjang.add(detailKeranjangs[item])
                totalPayment += detailKeranjangs[item].harga
            }
            tvTotalPaymentChecked.text = "Total : Rp $totalPayment"
            val alertDialog = AlertDialogFragment(
                "Konfirmasi pemesanan",
                "Apakah Anda yakin melanjutkan pemesanan?"
            )
            alertDialog.show(supportFragmentManager, null)
        }
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
                detailKeranjangs = list
            }
        }

        vmBasket.getBasketList().observe(this, basketListObserver)

//        cbAllCheckBox.setOnCheckedChangeListener { compoundButton, b ->
//            if(compoundButton.isChecked) {
//                if(detailKeranjangs.size > 0) {
//                    for (it in 0 until detailKeranjangs.size) {
//                        checkedItems.add(it)
//                    }
//                }
//            } else {
//                checkedItems.clear()
//            }
//        }
    }

    override fun addCheckedItem(pos: Int) {
        checkedItems.add(pos)
    }

    override fun addUncheckedItem(pos: Int) {
        checkedItems.remove(pos)
    }

    override fun getStatusAllChecked(): Boolean {
//        return cbAllCheckBox.isChecked
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun alertAction(tag: String?) {
        if(checkedItems.size > 0) vmBasket.insertBasketToOrder(newDetailKeranjang)
    }
}