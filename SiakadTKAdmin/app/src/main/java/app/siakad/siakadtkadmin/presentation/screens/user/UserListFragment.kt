package app.siakad.siakadtkadmin.presentation.screens.user

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
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.user.UserListViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.user.detail.verified.adapter.UserListAdapter

class UserListFragment(private val type: String) : Fragment() {
    private lateinit var tvUserCount: TextView
    private lateinit var rvUserList: RecyclerView

    private lateinit var vmUserList: UserListViewModel
    private lateinit var userListAdapter: UserListAdapter

    companion object {
        const val VERIFIED_USER = "verified_user"
        const val UNVERIFIED_USER = "unverified_user"

        fun getUserVerifiedListFragment(): UserListFragment {
            return UserListFragment(
                VERIFIED_USER
            )
        }

        fun getUserUnverifiedListFragment(): UserListFragment {
            return UserListFragment(
                UNVERIFIED_USER
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        tvUserCount = view.findViewById(R.id.tv_user_list_jumlah)

        setupListAdapter(view)

        setupViewModel()

        return view
    }

    private fun setupViewModel() {
        vmUserList = ViewModelProvider(
            this, ViewModelFactory(this.viewLifecycleOwner, this.context)
        ).get(UserListViewModel::class.java)
        if (type == VERIFIED_USER) {
            vmUserList.setUserType(true)
        } else {
            vmUserList.setUserType(false)
        }

        val obsUserList = Observer<ArrayList<Siswa>> { newUserList ->
            if (newUserList.size > 0) {
                userListAdapter.changeDataList(newUserList)
            }
        }
        vmUserList.getUserList().observe(this.viewLifecycleOwner, obsUserList)
    }

    private fun setupListAdapter(v: View?) {
        if (v != null) {
            userListAdapter = UserListAdapter()

            rvUserList = v.findViewById(R.id.rv_user_list_daftar_siswa)
            rvUserList.apply {
                setHasFixedSize(true)
                adapter = userListAdapter
                layoutManager = LinearLayoutManager(this.context)
            }
        }
    }
}