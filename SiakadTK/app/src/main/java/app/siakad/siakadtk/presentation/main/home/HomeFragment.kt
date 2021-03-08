package app.siakad.siakadtk.presentation.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.announcement.AnnouncementListActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.presentation.announcement.AnnouncementsData
import app.siakad.siakadtk.presentation.announcement.adapter.AnnouncementAdapter
import app.siakad.siakadtk.presentation.nota.NotaAdapter
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.presentation.announcement.AnnouncementViewModel
import app.siakad.siakadtk.presentation.announcement.inside.adapter.AnnouncementInsideAdapter
import app.siakad.siakadtk.presentation.nota.NotasData
import app.siakad.siakadtk.presentation.order.OrderListActivity
import app.siakad.siakadtk.presentation.registration.RegistrationActivity
import app.siakad.siakadtkadmin.presentation.utils.factory.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var tvSeeAllAnnouncement: TextView
    private lateinit var rvAnnouncement: RecyclerView
    private lateinit var tvStatusRegistrationTitle: TextView
    private lateinit var tvStatusRegistrationDesc: TextView
    private lateinit var ibtnStatusRegistration: ImageButton
    private lateinit var tvSeeAllOrderStatus: TextView
    private lateinit var rvOrderStatus: RecyclerView
    private lateinit var rvAnnouncementAdapter: AnnouncementAdapter
    private var listNota: ArrayList<PesananModel> = arrayListOf()

    private lateinit var vmAnnouncement: AnnouncementViewModel
    private lateinit var announcementListObserver: Observer<ArrayList<PengumumanModel>>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupItemView(view)
        setupView()
        setupObserver()
        return view
    }
    
    private fun setupItemView(v: View?) {
        if(v != null) {
            tvSeeAllAnnouncement = v.findViewById(R.id.tv_home_pengumuman_lihat_semua)
            rvAnnouncement = v.findViewById(R.id.rv_home_pengumuman_list)
            tvStatusRegistrationTitle = v.findViewById(R.id.tv_home_item_daful_title)
            tvStatusRegistrationDesc = v.findViewById(R.id.tv_home_item_daful_desc)
            ibtnStatusRegistration = v.findViewById(R.id.ibtn_home_item_daful)
            tvSeeAllOrderStatus = v.findViewById(R.id.tv_home_statuspesan_lihat_semua)
            rvOrderStatus = v.findViewById(R.id.rv_home_statuspesan_list)

            rvAnnouncement.setHasFixedSize(true)
            rvAnnouncementAdapter = AnnouncementAdapter()

            rvOrderStatus.setHasFixedSize(true)
            listNota.addAll(NotasData.listData)
            showNotaRecyclerList()
        }

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
    }

    private fun showNotaRecyclerList() {
        rvOrderStatus.layoutManager = LinearLayoutManager(this.context)
        val notaAdapter = NotaAdapter(listNota)
        rvOrderStatus.adapter = notaAdapter
    }

    private fun setupView() {

        rvAnnouncement.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = rvAnnouncementAdapter
        }

        vmAnnouncement = ViewModelProvider(
            this,
            ViewModelFactory(this.viewLifecycleOwner, this.requireContext())
        ).get(AnnouncementViewModel::class.java)

        tvSeeAllAnnouncement.setOnClickListener{
            val intent = Intent(this@HomeFragment.context, AnnouncementListActivity::class.java)
            startActivity(intent)
        }

        ibtnStatusRegistration.setOnClickListener{
            val intent = Intent(this@HomeFragment.context, RegistrationActivity::class.java)
            startActivity(intent)
        }

        tvSeeAllOrderStatus.setOnClickListener{
            val intent = Intent(this@HomeFragment.context, OrderListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupObserver() {
        announcementListObserver = Observer { list ->
            if (list.size > 0) {
                rvAnnouncementAdapter.changeDataList(list)
            }
        }

        vmAnnouncement.getAnnouncementList().observe(this.viewLifecycleOwner, announcementListObserver)
    }
}