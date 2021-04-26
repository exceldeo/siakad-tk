package app.siakad.siakadtkadmin.presentation.screens.main.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.main.dashboard.DashboardViewModel
import app.siakad.siakadtkadmin.presentation.screens.announcement.AnnouncementActivity
import app.siakad.siakadtkadmin.presentation.screens.classroom.ClassroomActivity
import app.siakad.siakadtkadmin.presentation.screens.order.OrderActivity
import app.siakad.siakadtkadmin.presentation.screens.registration.RegistrationActivity
import app.siakad.siakadtkadmin.presentation.screens.user.UserActivity

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var btnSeeAllAnnounce: ImageButton
    private lateinit var btnSeeAllClass: ImageButton
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

        tvNumUser = view.findViewById(R.id.tv_dashboard_jumlah_pengguna)
        tvNumUserUnver = view.findViewById(R.id.tv_dashboard_jumlah_pengguna_menunggu_verifikasi)
        rvActivity = view.findViewById(R.id.rv_dashboard_aktivitas)

        setupButtons(view)
        setupViewModel()

        return view
    }

    private fun setupButtons(v: View?) {
        if (v != null) {
            btnSeeAllAnnounce = v.findViewById(R.id.btn_dashboard_lihat_semua_pengumuman)
            btnSeeAllAnnounce.setOnClickListener{
                val intent = Intent(this@DashboardFragment.context, AnnouncementActivity::class.java)
                startActivity(intent)
            }

            btnSeeAllClass = v.findViewById(R.id.btn_dashboard_lihat_semua_kelas)
            btnSeeAllClass.setOnClickListener{
                val intent = Intent(this@DashboardFragment.context, ClassroomActivity::class.java)
                startActivity(intent)
            }

            btnSeeAllUser = v.findViewById(R.id.btn_dashboard_lihat_semua_pengguna)
            btnSeeAllUser.setOnClickListener{
                val intent = Intent(this@DashboardFragment.context, UserActivity::class.java)
                startActivity(intent)
            }

            btnOrder = v.findViewById(R.id.btn_dashboard_data_pesanan)
            btnOrder.setOnClickListener{
                val intent = Intent(this@DashboardFragment.context, OrderActivity::class.java)
                startActivity(intent)
            }

            btnRegistration = v.findViewById(R.id.btn_dashboard_data_daful)
            btnRegistration.setOnClickListener{
                val intent = Intent(this@DashboardFragment.context, RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupViewModel() {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
    }
}