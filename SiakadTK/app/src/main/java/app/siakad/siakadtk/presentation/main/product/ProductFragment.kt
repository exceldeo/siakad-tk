package app.siakad.siakadtk.presentation.main.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import app.siakad.siakadtk.presentation.main.product.basket.BasketActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.viewmodels.main.product.ProductViewModel
import app.siakad.siakadtk.presentation.main.product.detail.book.ProductBookListActivity
import app.siakad.siakadtk.presentation.main.product.detail.uniform.ProductUniformListActivity

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
//    private lateinit var svProduct: SearchView
    private lateinit var btnMyBasket: LinearLayout
    private lateinit var btnBookProduct: ImageButton
    private lateinit var btnUniformProduct: ImageButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        productViewModel =
                ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        setupItemView(view)
        setupView()
        return view
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
        btnBookProduct.setOnClickListener{
            val intent = Intent(this@ProductFragment.context, ProductBookListActivity::class.java)
            startActivity(intent)
        }
        btnUniformProduct.setOnClickListener{
            val intent = Intent(this@ProductFragment.context, ProductUniformListActivity::class.java)
            startActivity(intent)
        }
    }
}