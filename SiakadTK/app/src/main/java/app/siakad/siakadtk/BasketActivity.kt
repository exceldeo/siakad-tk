package app.siakad.siakadtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class BasketActivity : AppCompatActivity() {

    private lateinit var rvBasket: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        setupItemWiew()
    }

    private fun setupItemWiew() {
        rvBasket = findViewById(R.id.rv_basket_order_nota_list)
    }
}