package app.siakad.siakadtk.presentation.screens.main.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.presentation.screens.history.HistoryActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile.ProfileViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.profile.SettingsActivity
import app.siakad.siakadtk.presentation.screens.registration.RegistrationActivity

class ProfileFragment : Fragment() {

    private lateinit var vmProfile: ProfileViewModel
    private lateinit var ibtnHistory: ImageButton
    private lateinit var ibtnRegistration: ImageButton
    private lateinit var tvStudentName: TextView
    private lateinit var tvClassStudent: TextView

    private lateinit var ivSetting: ImageView

//    private lateinit var rvMyActivity: RecyclerView
//    private var list: ArrayList<Aktivitas> = arrayListOf()

    private var dataUser = Pengguna()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setupViewModel()
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
            ivSetting = v.findViewById(R.id.iv_profile_btn_pengaturan)

//            rvMyActivity = v.findViewById(R.id.rv_profile_activity_list)
//            rvMyActivity.setHasFixedSize(true)

//            list.addAll(UserActivitiesData.listData)
//            showMyActivityRecyclerList()
        }
    }

    private fun setupViewModel() {
        vmProfile = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this.requireContext()
            )
        ).get(ProfileViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
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

        val obsProfileGetUser = Observer<Pengguna> {
            dataUser = it
            tvStudentName.text = "Nama Siswa : " + it.nama
            tvClassStudent.text = "Kelas : " + it.detail!!.kelas
        }
        vmProfile.getUserData()
            .observe(this.viewLifecycleOwner, obsProfileGetUser)
    }

//    private fun showMyActivityRecyclerList() {
//        rvMyActivity.layoutManager = LinearLayoutManager(this.context)
//        val listUserActivitiesAdapter = UserActivitiesAdapter(list)
//        rvMyActivity.adapter = listUserActivitiesAdapter
//    }
}