package app.siakad.siakadtkadmin.presentation.screens.announcement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.announcement.adapter.AnnouncementListAdater

class AnnouncementListFragment(private val type: String) : Fragment() {
    private lateinit var tvAnnouncementCount: TextView
    private lateinit var rvAnnouncementList: RecyclerView

    private lateinit var vmAnnouncementList: AnnouncementListViewModel
    private lateinit var announcementListAdapter: AnnouncementListAdater

    private lateinit var svAnnouncement: SearchView
    private lateinit var ivAddAnnouncement: ImageView

    companion object {
        const val ANNOUNCEMENT_TYPE = "Announcement_Type"
        const val TO_ALL = "Semua"
        const val TO_SISWA = "Siswa"
        const val TO_KELAS = "Kelas"

        fun getAllAnnouncementListFragment(): AnnouncementListFragment {
            return AnnouncementListFragment(
                TO_ALL
            )
        }

        fun getUserAnnouncementListFragment(): AnnouncementListFragment {
            return AnnouncementListFragment(
                TO_SISWA
            )
        }

        fun getClassAnnouncementListFragment(): AnnouncementListFragment {
            return AnnouncementListFragment(
                TO_KELAS
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_announcement_list, container, false)

        tvAnnouncementCount = view.findViewById(R.id.tv_announcement_list_jumlah_pengumuman)
        svAnnouncement = view.findViewById(R.id.sv_announcement_list_cari)

        setupButton(view)
        setupListAdapter(view)
        setupViewModel()

        return view
    }

    private fun setupButton(v: View?) {
        if (v != null) {
            ivAddAnnouncement = v.findViewById(R.id.iv_announcement_list_tambah_announ)
            ivAddAnnouncement.setOnClickListener {
                val intent = Intent(this.context, AnnouncementAddActivity::class.java)
                intent.putExtra(ANNOUNCEMENT_TYPE, type)
                startActivity(intent)
            }
        }
    }

    private fun setupViewModel() {
        vmAnnouncementList = ViewModelProvider(
            this, ViewModelFactory(this.viewLifecycleOwner, this.context)
        ).get(AnnouncementListViewModel::class.java)
        vmAnnouncementList.setAnnouncementType(type)

        val obsAnnouncementList = Observer<ArrayList<Pengumuman>> { newAnnouncementList ->
            if (newAnnouncementList.size > 0) {
                announcementListAdapter.changeDataList(newAnnouncementList)
            }
        }
        vmAnnouncementList.getAnnouncementList()
            .observe(this.viewLifecycleOwner, obsAnnouncementList)
    }

    private fun setupListAdapter(v: View?) {
        if (v != null) {
            rvAnnouncementList = v.findViewById(R.id.rv_announcement_list_daftar_pengumuman)
            announcementListAdapter = AnnouncementListAdater()
            rvAnnouncementList.apply {
                setHasFixedSize(true)
                adapter = announcementListAdapter
                layoutManager = LinearLayoutManager(this.context)
            }
        }
    }
}