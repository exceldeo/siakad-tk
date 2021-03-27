package app.siakad.siakadtkadmin.presentation.screens.user

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.utils.adapter.ViewPagerAdapter

class UserActivity : AppCompatActivity() {

    private val pageTitle = "Pengguna"

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setupAppBar()
        setupView()
    }

    private fun setupView() {
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
        pagerAdapter.addFragment("Terverifikasi", UserListFragment.getUserVerifiedListFragment())
        pagerAdapter.addFragment("Belum Diverifikasi", UserListFragment.getUserUnverifiedListFragment())

        viewPager = findViewById(R.id.view_pager_user)
        viewPager.adapter = pagerAdapter

        tab = findViewById(R.id.tabs_user)
        tab.setupWithViewPager(viewPager)
    }
}