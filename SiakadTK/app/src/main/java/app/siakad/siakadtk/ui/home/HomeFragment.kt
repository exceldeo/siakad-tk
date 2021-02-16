package app.siakad.siakadtk.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var tvSeeAllAnnouncement: TextView
    private lateinit var rvAnnouncement: RecyclerView
    private lateinit var tvSeeAllRegistration: TextView
    private lateinit var rvRegistration: RecyclerView
    private lateinit var tvSeeAllOrderStatus: TextView
    private lateinit var rvOrderStatus: RecyclerView
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupItemView(view)
        return view
    }
    
    private fun setupItemView(v: View?) {
        if(v != null) {
            tvSeeAllAnnouncement = v.findViewById(R.id.tv_home_pengumuman_lihat_semua)
            rvAnnouncement = v.findViewById(R.id.rv_home_pengumuman_list)
            tvSeeAllRegistration = v.findViewById(R.id.tv_home_daful_lihat_semua)
            rvRegistration = v.findViewById(R.id.tv_home_daful_lihat_semua)
            tvSeeAllOrderStatus = v.findViewById(R.id.tv_home_statuspesan_lihat_semua)
            rvOrderStatus = v.findViewById(R.id.rv_home_statuspesan_list)
        }

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
    }
}