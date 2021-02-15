package app.siakad.siakadtkadmin.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var tvNumAnnounce: TextView
    private lateinit var tvNumnotif: TextView
    private lateinit var btnSeeAllAnnounce: TextView
    private lateinit var tvNumUser: TextView
    private lateinit var tvNumUserUnver: TextView
    private lateinit var btnSeeAllUser: TextView
    private lateinit var btnOrder: ImageButton
    private lateinit var btnRegistration: ImageButton
    private lateinit var rvActivity: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setupItemView(view)
        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            tvNumAnnounce = v.findViewById(R.id.tv_dashboard_jumlah_pengumuman)
            tvNumnotif = v.findViewById(R.id.tv_dashboard_jumlah_notifikasi)
            btnSeeAllAnnounce = v.findViewById(R.id.btn_dashboard_lihat_semua_pengumuman)
            tvNumUser = v.findViewById(R.id.tv_dashboard_jumlah_pengguna)
            tvNumUserUnver = v.findViewById(R.id.tv_dashboard_jumlah_pengguna_menunggu_verifikasi)
            btnSeeAllUser = v.findViewById(R.id.btn_dashboard_lihat_semua_pengguna)
            btnOrder = v.findViewById(R.id.btn_dashboard_data_pesanan)
            btnRegistration = v.findViewById(R.id.btn_dashboard_data_daful)
            rvActivity = v.findViewById(R.id.rv_dashboard_aktivitas)
        }

        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }
}