package app.siakad.siakadtkadmin.presentation.screens.registration

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.user.UserListFragment
import app.siakad.siakadtkadmin.presentation.utils.adapter.ViewPagerAdapter

class RegistrationActivity : AppCompatActivity() {

    private val pageTitle = "Daftar Ulang"

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setupAppBar()

        setupTabLayout()
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupTabLayout() {
        pagerAdapter =
            ViewPagerAdapter(
                this,
                supportFragmentManager
            )
        pagerAdapter.addFragment(
            "Terverifikasi",
            RegistrationListFragment.getRegistrationVerifiedListFragment()
        )
        pagerAdapter.addFragment(
            "Belum Diverifikasi",
            RegistrationListFragment.getRegistrationUnverifiedListFragment()
        )

        viewPager = findViewById(R.id.view_pager_registration)
        viewPager.adapter = pagerAdapter

        tab = findViewById(R.id.tabs_registration)
        tab.setupWithViewPager(viewPager)
    }
}