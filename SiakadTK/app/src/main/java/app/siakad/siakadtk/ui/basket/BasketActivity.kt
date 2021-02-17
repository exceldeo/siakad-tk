package app.siakad.siakadtk.ui.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class BasketActivity : AppCompatActivity() {

    private lateinit var rvBasket: RecyclerView
    private lateinit var ibtnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        setupItemWiew()
        setupView()
    }

    private fun setupItemWiew() {
        rvBasket = findViewById(R.id.rv_basket_order_nota_list)
        ibtnBack = findViewById(R.id.ibtn_basket_back)
    }

    private fun setupView() {
        ibtnBack.setOnClickListener{
        }
    }
}