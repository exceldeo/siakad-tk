package app.siakad.siakadtk.presentation.screens.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.screens.announcement.AnnouncementListActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.presentation.screens.announcement.adapter.AnnouncementAdapter
import app.siakad.siakadtk.presentation.screens.order.adapter.OrderAdapter
import app.siakad.siakadtk.infrastructure.data.Pengumuman
import app.siakad.siakadtk.infrastructure.data.Pesanan
import app.siakad.siakadtk.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtk.presentation.screens.order.OrderListActivity
import app.siakad.siakadtk.presentation.screens.registration.RegistrationActivity
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.home.HomeViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.order.OrderViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.screens.registration.RegistrationFormViewModel
import app.siakad.siakadtk.presentation.screens.announcement.AnnouncementDetailActivity
import app.siakad.siakadtk.presentation.screens.announcement.inside.adapter.AnnouncementInsideAdapter
import app.siakad.siakadtk.presentation.screens.order.detail.OrderDetailActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var tvSeeAllAnnouncement: TextView
    private lateinit var flAnnouncement: FrameLayout
    private lateinit var rvAnnouncement: RecyclerView
    private lateinit var tvStatusRegistrationTitle: TextView
    private lateinit var tvStatusRegistrationDesc: TextView
    private lateinit var ibtnStatusRegistration: ImageButton
    private lateinit var tvSeeAllOrderStatus: TextView
    private lateinit var hsvAnnouncement: HorizontalScrollView
    private lateinit var rvOrder: RecyclerView
    private lateinit var rvAnnouncementAdapter: AnnouncementAdapter
    private lateinit var rvOrderAdapter: OrderAdapter

    private var dataUser = Pengguna()
    private lateinit var vmRegistrationForm: RegistrationFormViewModel

    private lateinit var vmOrderList: OrderViewModel
    private lateinit var orderListObserver: Observer<ArrayList<Pesanan>>

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

        vmAnnouncement.setAnnouncementByUserId()
        return view
    }
    
    private fun setupItemView(v: View?) {
        if(v != null) {
            tvSeeAllAnnouncement = v.findViewById(R.id.tv_home_pengumuman_lihat_semua)
            flAnnouncement = v.findViewById(R.id.fl_home_pengumuman_jika_kosong)
            hsvAnnouncement = v.findViewById(R.id.hs_home_pengumuman_list)
            rvAnnouncement = v.findViewById(R.id.rv_home_pengumuman_list)
            tvStatusRegistrationTitle = v.findViewById(R.id.tv_home_item_daful_title)
            tvStatusRegistrationDesc = v.findViewById(R.id.tv_home_item_daful_desc)
            ibtnStatusRegistration = v.findViewById(R.id.ibtn_home_item_daful)
            tvSeeAllOrderStatus = v.findViewById(R.id.tv_home_statuspesan_lihat_semua)
            rvOrder = v.findViewById(R.id.rv_home_statuspesan_list)

            rvAnnouncement.setHasFixedSize(true)
            rvAnnouncementAdapter = AnnouncementAdapter()

            rvOrder.setHasFixedSize(true)
            rvOrderAdapter = OrderAdapter()
        }

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private fun setupView() {

        rvAnnouncement.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = rvAnnouncementAdapter
        }

        rvAnnouncementAdapter.setOnItemClickCallback(object: AnnouncementAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PengumumanModel) {
                val intent = Intent(this@HomeFragment.context, AnnouncementDetailActivity::class.java)
                intent.putExtra("pengumuman", data);
                startActivity(intent)
            }
        })

        vmAnnouncement = ViewModelProvider(
            this,
            ViewModelFactory(this.viewLifecycleOwner, this.requireContext())
        ).get(AnnouncementViewModel::class.java)

        rvOrderAdapter.setOnItemClickCallback(object: OrderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Pesanan) {
                val intent = Intent(this@HomeFragment.context, OrderDetailActivity::class.java)
                intent.putExtra("pesanan", data);
                startActivity(intent)
            }
        })

        rvOrder.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.VERTICAL, false)
            adapter = rvOrderAdapter
        }

        vmOrderList = ViewModelProvider(
            this,
            ViewModelFactory(this.viewLifecycleOwner, this.requireContext())
        ).get(OrderViewModel::class.java)

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

        vmRegistrationForm = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this.requireContext()
            )
        ).get(RegistrationFormViewModel::class.java)

        val obsRegistrationGetUser = Observer<Pengguna> {
            dataUser = it

            vmAnnouncement.setAnnouncementByClass(it.detail!!.kelasId)

            if (it.detail!!.kelasId == "") {
                tvStatusRegistrationTitle.text = "Anda belum melakukan daftar ulang"
                tvStatusRegistrationDesc.text = "Silahkan melakukan daftar ulang, sebelum\n" + "tanggal 31 Januari."
                ibtnStatusRegistration.setImageResource(R.drawable.ic_daful_start)
            }
            else
            {
                if(it.detail!!.dafulState)
                {
                    tvStatusRegistrationTitle.text = "Daftar ulang telah dilakukan, terimaksih!"
                    tvStatusRegistrationDesc.text = "Daftar ulang selesai dan kami terima."
                    ibtnStatusRegistration.setImageResource(R.drawable.ic_daful_selesai)
                } else {
                    tvStatusRegistrationTitle.text = "Terimakasih telah melakukan daftar ulang"
                    tvStatusRegistrationDesc.text = "Lihat secara berkala status daftar ulang anda."
                    ibtnStatusRegistration.setImageResource(R.drawable.ic_daful_proses)
                }
            }
        }
        vmRegistrationForm.getUserData()
            .observe(this.viewLifecycleOwner, obsRegistrationGetUser)
    }

    private fun setupObserver() {
        announcementListObserver = Observer { list ->
            if (list.size > 0) {
                hsvAnnouncement.visibility = View.VISIBLE
                flAnnouncement.visibility = View.GONE
                rvAnnouncementAdapter.changeDataList(list)
            }
        }

        vmAnnouncement.getAnnouncementList().observe(this.viewLifecycleOwner, announcementListObserver)


        orderListObserver = Observer { list ->
            var undoneList = arrayListOf<Pesanan>()
            for (item in list)
            {
                if(item.pesanan.statusPesan != OrderStateModel.ORDER_DONE.str)
                    undoneList.add(item)
            }
            rvOrderAdapter.changeDataList(undoneList)
        }

        vmOrderList.getOrderList().observe(this.viewLifecycleOwner, orderListObserver)
    }
}