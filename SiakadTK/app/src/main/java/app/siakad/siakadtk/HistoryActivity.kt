package app.siakad.siakadtk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.ui.profile.ProfileFragment

class HistoryActivity : AppCompatActivity() {

    private lateinit var rvHistory: RecyclerView
    private lateinit var ibtnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setupItemWiew()
        setupView()
    }

    private fun setupItemWiew() {
        rvHistory = findViewById(R.id.rv_history_order_list)
        ibtnBack = findViewById(R.id.ibtn_history_back)
    }

    private fun setupView() {
        ibtnBack.setOnClickListener{

        }
    }
}