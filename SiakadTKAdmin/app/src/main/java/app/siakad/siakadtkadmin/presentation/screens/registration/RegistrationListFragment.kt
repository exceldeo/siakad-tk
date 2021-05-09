package app.siakad.siakadtkadmin.presentation.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.DaftarUlang
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.registration.RegistrationListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.registration.adapter.RegistrationListAdapter

class RegistrationListFragment(private val type: String) : Fragment() {
    private lateinit var tvRegistrationCount: TextView
    private lateinit var rvRegistrationList: RecyclerView

    private lateinit var vmRegistrationList: RegistrationListViewModel
    private lateinit var registrationListAdapter: RegistrationListAdapter

    companion object {
        const val VERIFIED_REGISTRATIOIN = "verified_registration"
        const val UNVERIFIED_REGISTRATIOIN = "unverified_registration"

        fun getRegistrationVerifiedListFragment(): RegistrationListFragment {
            return RegistrationListFragment(
                VERIFIED_REGISTRATIOIN
            )
        }

        fun getRegistrationUnverifiedListFragment(): RegistrationListFragment {
            return RegistrationListFragment(
                UNVERIFIED_REGISTRATIOIN
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration_list, container, false)

        tvRegistrationCount = view.findViewById(R.id.tv_registration_jumlah)

        setupListAdapter(view)
        setupViewModel()

        return view
    }

    private fun setupViewModel() {
        vmRegistrationList = ViewModelProvider(
            this, ViewModelFactory(this.viewLifecycleOwner, this.context)
        ).get(RegistrationListViewModel::class.java)
        if (type == VERIFIED_REGISTRATIOIN) {
            vmRegistrationList.setRegistrationType(true)
        } else {
            vmRegistrationList.setRegistrationType(false)
        }

        val obsRegistrationList = Observer<ArrayList<DaftarUlang>> { newRegistrationList ->
            if (newRegistrationList.size > 0) {
                registrationListAdapter.changeDataList(newRegistrationList)
            }
        }
        vmRegistrationList.getRegistrationList()
            .observe(this.viewLifecycleOwner, obsRegistrationList)
    }

    private fun setupListAdapter(v: View?) {
        if (v != null) {
            registrationListAdapter = RegistrationListAdapter()

            rvRegistrationList = v.findViewById(R.id.rv_registration_daftar_siswa)
            rvRegistrationList.apply {
                setHasFixedSize(true)
                adapter = registrationListAdapter
                layoutManager = LinearLayoutManager(this.context)
            }
        }
    }
}