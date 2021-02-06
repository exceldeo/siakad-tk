package app.siakad.siakadtkadmin.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.siakad.siakadtkadmin.R

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        productViewModel =
                ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_product, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        productViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}