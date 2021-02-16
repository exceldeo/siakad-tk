package app.siakad.siakadtk.ui.profile

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
import app.siakad.siakadtk.R
import org.w3c.dom.Text

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var ibtnHistory: ImageButton
    private lateinit var ibtnRegistration: ImageButton
    private lateinit var tvStudentName: TextView
    private lateinit var tvClassStudent: TextView
    private lateinit var rvMyActivity: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setupItemView(view)
        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            ibtnHistory = v.findViewById(R.id.ibtn_profile_riwayat_beli)
            ibtnRegistration = v.findViewById(R.id.ibtn_profile_daful)
            tvStudentName = v.findViewById(R.id.tv_profile_nama_siswa)
            tvClassStudent = v.findViewById(R.id.tv_profile_kelas_siswa)
            rvMyActivity = v.findViewById(R.id.rv_profile_activity_list)
        }
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }
}