package app.siakad.siakadtkadmin.presentation.main.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.announcement.AnnouncementActivity
import app.siakad.siakadtkadmin.presentation.notification.NotificationActivity
import app.siakad.siakadtkadmin.presentation.order.OrderActivity
import app.siakad.siakadtkadmin.presentation.registration.RegistrationActivity
import app.siakad.siakadtkadmin.presentation.user.UserActivity

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var btnSeeAllAnnounce: ImageButton
    private lateinit var btnSeeAllNotification: ImageButton
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
        setupView()
        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            btnSeeAllAnnounce = v.findViewById(R.id.btn_dashboard_lihat_semua_pengumuman)
            btnSeeAllNotification = v.findViewById(R.id.btn_dashboard_lihat_semua_notifikasi)
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

    private fun setupView() {
        btnSeeAllAnnounce.setOnClickListener{
            val intent = Intent(this@DashboardFragment.context, AnnouncementActivity::class.java)
            startActivity(intent)
        }

        btnSeeAllNotification.setOnClickListener{
            val intent = Intent(this@DashboardFragment.context, NotificationActivity::class.java)
            startActivity(intent)
        }

        btnSeeAllUser.setOnClickListener{
            val intent = Intent(this@DashboardFragment.context, UserActivity::class.java)
            startActivity(intent)
        }

        btnOrder.setOnClickListener{
            val intent = Intent(this@DashboardFragment.context, OrderActivity::class.java)
            startActivity(intent)
        }

        btnRegistration.setOnClickListener{
            val intent = Intent(this@DashboardFragment.context, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}