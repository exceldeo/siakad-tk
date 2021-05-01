package app.siakad.siakadtkadmin.presentation.screens.classroom

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
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.ClassroomListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.classroom.adapter.ClassroomListAdater

class ClassroomListFragment(private val type: String) : Fragment() {
    private lateinit var tvClassroomCount: TextView
    private lateinit var rvClassroomList: RecyclerView

    private lateinit var vmClassroomList: ClassroomListViewModel
    private lateinit var classroomListAdapter: ClassroomListAdater

    private lateinit var svClassroom: SearchView
    private lateinit var ivAddClassroom: ImageView

    companion object {
        const val CLASSROOM_TYPE = "Classroom_Type"
        const val TK_A = "TK A"
        const val TK_B = "TK B"

        fun getTKAListFragment(): ClassroomListFragment {
            return ClassroomListFragment(
                TK_A
            )
        }

        fun getTKBListFragment(): ClassroomListFragment {
            return ClassroomListFragment(
                TK_B
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_classroom_list, container, false)

        tvClassroomCount = view.findViewById(R.id.tv_classroom_list_jumlah_kelas)
        svClassroom = view.findViewById(R.id.sv_classroom_list_cari)

        setupButton(view)
        setupListAdapter(view)
        setupViewModel()

        return view
    }

    private fun setupButton(v: View?) {
        if (v != null) {
            ivAddClassroom = v.findViewById(R.id.btn_classroom_list_tambah_class)
            ivAddClassroom.setOnClickListener {
                val intent = Intent(this.context, ClassroomAddActivity::class.java)
                intent.putExtra(CLASSROOM_TYPE, type)
                startActivity(intent)
            }
        }
    }

    private fun setupViewModel() {
        vmClassroomList = ViewModelProvider(
            this, ViewModelFactory(this.viewLifecycleOwner, this.context)
        ).get(ClassroomListViewModel::class.java)
        vmClassroomList.setClassroomType(type)

        val obsClassroomList = Observer<ArrayList<KelasModel>> { newClassroomList ->
            if (newClassroomList.size > 0) {
                classroomListAdapter.changeDataList(newClassroomList)
            }
        }
        vmClassroomList.getClassroomList()
            .observe(this.viewLifecycleOwner, obsClassroomList)
    }

    private fun setupListAdapter(v: View?) {
        if (v != null) {
            rvClassroomList = v.findViewById(R.id.rv_classroom_list_daftar_kelas)
            classroomListAdapter = ClassroomListAdater()
            rvClassroomList.apply {
                setHasFixedSize(true)
                adapter = classroomListAdapter
                layoutManager = LinearLayoutManager(this.context)
            }
        }
    }
}