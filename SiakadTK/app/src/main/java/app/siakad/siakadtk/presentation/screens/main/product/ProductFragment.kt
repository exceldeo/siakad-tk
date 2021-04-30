package app.siakad.siakadtk.presentation.screens.main.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import app.siakad.siakadtk.presentation.screens.main.product.basket.BasketActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.product.ProductListViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile.ProfileViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.product.ProductListActivity

class ProductFragment : Fragment() {

    private lateinit var vmProductList: ProductListViewModel
//    private lateinit var svProduct: SearchView
    private lateinit var btnMyBasket: LinearLayout
    private lateinit var btnBookProduct: ImageButton
    private lateinit var btnUniformProduct: ImageButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setupViewModel()
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        setupItemView(view)
        setupView()
        return view
    }

    private fun setupViewModel() {
        vmProductList = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this.requireContext()
            )
        ).get(ProductListViewModel::class.java)
    }

    private fun setupItemView(v: View?) {
        if(v != null) {
//            svProduct = v.findViewById(R.id.sv_product_cari_produk)
            btnMyBasket = v.findViewById(R.id.btn_product_keranjang_saya)
            btnBookProduct = v.findViewById(R.id.btn_product_book)
            btnUniformProduct  = v.findViewById(R.id.btn_product_seragam)
        }
    }

    private fun setupView() {
        btnMyBasket.setOnClickListener{
            val intent = Intent(this@ProductFragment.context, BasketActivity::class.java)
            startActivity(intent)
        }
        btnUniformProduct.setOnClickListener {
            val intent = Intent(this@ProductFragment.activity, ProductListActivity::class.java)
            intent.putExtra(ProductListActivity.PAGE_TYPE, ProductListActivity.UNIFORM_PAGE)
            startActivity(intent)
        }
        btnBookProduct.setOnClickListener {
            val intent = Intent(this@ProductFragment.activity, ProductListActivity::class.java)
            intent.putExtra(ProductListActivity.PAGE_TYPE, ProductListActivity.BOOK_PAGE)
            startActivity(intent)
        }
    }
}