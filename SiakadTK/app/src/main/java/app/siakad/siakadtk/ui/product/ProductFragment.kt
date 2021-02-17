package app.siakad.siakadtk.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.ui.basket.BasketActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.ui.announcement.Announcement
import app.siakad.siakadtk.ui.nota.ListNotaAdapter

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var svProduct: SearchView
    private lateinit var btnMyBasket: LinearLayout
    private lateinit var rvProduct: RecyclerView
    private var listProduct: ArrayList<Product> = arrayListOf()

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
            svProduct = v.findViewById(R.id.sv_product_cari_produk)
            btnMyBasket = v.findViewById(R.id.btn_product_keranjang_saya)
            rvProduct =v.findViewById(R.id.rv_product_produk_list)

            rvProduct.setHasFixedSize(true)
            listProduct.addAll(ProductsData.listData)
            showProductRecyclerList()
        }
    }

    private fun showProductRecyclerList() {
        rvProduct.layoutManager = GridLayoutManager(this.context, 2)
        val listProductAdapter = ListProductAdapter(listProduct)
        rvProduct.adapter = listProductAdapter
    }

    private fun setupView() {
        btnMyBasket.setOnClickListener{
            val intent = Intent(this@ProductFragment.context, BasketActivity::class.java)
            startActivity(intent)
        }
    }
}