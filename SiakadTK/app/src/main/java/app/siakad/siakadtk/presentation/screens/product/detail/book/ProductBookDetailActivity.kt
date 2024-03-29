package app.siakad.siakadtk.presentation.screens.main.product.detail.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.KeranjangModel
import app.siakad.siakadtk.domain.models.product.BukuModel
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.infrastructure.viewmodels.screens.basket.KeranjangViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.registration.RegistrationFormViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_product_detail.view.*

class ProductBookDetailActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val pageTitle = "Produk Buku"

    private lateinit var toolbar: Toolbar
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var etProductSum: EditText
    private lateinit var ivProductImage: ImageView
    private lateinit var tvProductTotalPayment: TextView
    private lateinit var btnProductAddToBasket: TextView

    private lateinit var vmBasket: KeranjangViewModel
    private var item = DetailKeranjangModel()
    private var limitOrder = 100

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_book_detail)
        setupItemView()
        setupView()

        val data = intent.getParcelableExtra<Parcelable>("buku") as BukuModel
        limitOrder = data.jumlah
        item.nama = data.namaProduk
        item.gambar = data.fotoProduk
        item.jumlah = data.jumlah
        item.harga = data.harga
        item.produkId = data.produkId

        Picasso.with(this.applicationContext).load(item.gambar).into(ivProductImage)
        tvProductName.text = item.nama
        tvProductPrice.text = item.harga.toString()
        etProductSum.setText(item.jumlah.toString())
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        ivProductImage = findViewById(R.id.iv_product_bookdetail_img)
        tvProductName = findViewById(R.id.tv_product_bookdetail_nama)
        tvProductPrice = findViewById(R.id.tv_product_bookdetail_harga)
        etProductSum = findViewById(R.id.et_product_bookdetail_jumlah)
        tvProductTotalPayment = findViewById(R.id.tv_product_bookdetail_total_harga)
        btnProductAddToBasket = findViewById(R.id.btn_product_bookdetail_tambah_ke_basket)

        etProductSum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (etProductSum.text.isEmpty() || etProductSum.text.toString() == "") {
                    item.jumlah = 0
                }
                else {
                    item.jumlah = Integer.valueOf(etProductSum.text.toString())
                }
                tvProductTotalPayment.text = "Total : Rp " + (Integer.valueOf(item.harga) * item.jumlah).toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupView() {
        setupAppBar()

        vmBasket = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(KeranjangViewModel::class.java)

        var isClicked = false
        btnProductAddToBasket.setOnClickListener{
            if(validateInput() && limitOrder > 0) {
                if (!isClicked) {
                    isClicked = true
                    vmBasket.insertItemBasket(
                        name = item.nama,
                        image = item.gambar,
                        jumlah = Integer.valueOf(item.jumlah),
                        harga = item.harga * item.jumlah,
                        produkId = item.produkId
                    )
                }
                else {
                    showToast(application.applicationContext.getString(R.string.wait_process))
                }
            } else if (!validateInput() && limitOrder > 0) {
                showToast(application.applicationContext.getString(R.string.wrong_input))
            }
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun validateInput(): Boolean {
        var returnState = true
        if (limitOrder == 0) {
            showToast(application.applicationContext.getString(R.string.sold_out))
            returnState = false
        } else if (Integer.valueOf(etProductSum.text.toString()) > limitOrder) {
            etProductSum.error = getString(R.string.over_order)
            returnState = false
        } else if (Integer.valueOf(etProductSum.text.toString()) <= 0) {
            etProductSum.error = getString(R.string.min_order)
            returnState = false
        }
        return returnState
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}

