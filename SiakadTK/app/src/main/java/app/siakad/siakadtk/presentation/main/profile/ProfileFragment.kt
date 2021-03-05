package app.siakad.siakadtk.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.history.HistoryActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.AktivitasModel
import app.siakad.siakadtk.presentation.registration.RegistrationActivity

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var ibtnHistory: ImageButton
    private lateinit var ibtnRegistration: ImageButton
    private lateinit var tvStudentName: TextView
    private lateinit var tvClassStudent: TextView
    private lateinit var rvMyActivity: RecyclerView
    private lateinit var ivSetting: ImageView
    private var list: ArrayList<AktivitasModel> = arrayListOf()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setupItemView(view)
        setupView()
        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            ibtnHistory = v.findViewById(R.id.ibtn_profile_riwayat_beli)
            ibtnRegistration = v.findViewById(R.id.ibtn_profile_daful)
            tvStudentName = v.findViewById(R.id.tv_profile_nama_siswa)
            tvClassStudent = v.findViewById(R.id.tv_profile_kelas_siswa)
            rvMyActivity = v.findViewById(R.id.rv_profile_activity_list)
            ivSetting = v.findViewById(R.id.iv_profile_btn_pengaturan)

            rvMyActivity.setHasFixedSize(true)

            list.addAll(UserActivitiesData.listData)
            showMyActivityRecyclerList()
        }
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    private fun setupView() {
        ivSetting.setOnClickListener{
            val intent = Intent(this@ProfileFragment.context, SettingsActivity::class.java)
            startActivity(intent)
        }

        ibtnHistory.setOnClickListener{
            val intent = Intent(this@ProfileFragment.context, HistoryActivity::class.java)
            startActivity(intent)
        }

        ibtnRegistration.setOnClickListener{
            val intent = Intent(this@ProfileFragment.context, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showMyActivityRecyclerList() {
        rvMyActivity.layoutManager = LinearLayoutManager(this.context)
        val listUserActivitiesAdapter = UserActivitiesAdapter(list)
        rvMyActivity.adapter = listUserActivitiesAdapter
    }
}