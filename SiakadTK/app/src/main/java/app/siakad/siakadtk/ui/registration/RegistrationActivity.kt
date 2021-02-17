package app.siakad.siakadtk.ui.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.ui.order.OrderListActivity

class RegistrationActivity : AppCompatActivity() {

    private lateinit var ibtnBack: ImageButton
    private lateinit var tvRegistrationStatus: TextView
    private lateinit var ivRegistrationStatus: ImageView
    private lateinit var btnRegistrationForm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        ibtnBack = findViewById(R.id.ibtn_registration_back)
        tvRegistrationStatus = findViewById(R.id.tv_registration_desc_status)
        ivRegistrationStatus = findViewById(R.id.iv_registration_status)
        btnRegistrationForm = findViewById(R.id.btn_registration_go_to_form)
    }

    private fun setupView() {
        ibtnBack.setOnClickListener{

        }
        btnRegistrationForm.setOnClickListener{
            val intent = Intent(this@RegistrationActivity, RegistrationFormActivity::class.java)
            startActivity(intent)
        }
    }
}