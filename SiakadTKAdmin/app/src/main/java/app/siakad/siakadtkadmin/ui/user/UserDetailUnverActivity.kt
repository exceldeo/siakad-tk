package app.siakad.siakadtkadmin.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class UserDetailUnverActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPasswd: TextView
    private lateinit var btnProofPay: CardView
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_unver)
    }

    private fun setupItemView() {
        tvName = findViewById(R.id.tv_user_detail_unver_nama)
        tvEmail = findViewById(R.id.tv_user_detail_unver_email)
        tvPasswd = findViewById(R.id.tv_user_detail_unver_passwd)
        btnProofPay = findViewById(R.id.btn_user_detail_unver_buktibayar)
        btnCancel = findViewById(R.id.btn_user_detail_unver_batal)
        btnSave = findViewById(R.id.btn_user_detail_unver_simpan)
    }
}