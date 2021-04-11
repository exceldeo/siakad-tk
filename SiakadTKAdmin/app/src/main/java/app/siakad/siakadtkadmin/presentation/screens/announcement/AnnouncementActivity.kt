package app.siakad.siakadtkadmin.presentation.screens.announcement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Pengumuman
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.announcement.AnnouncementListViewModel
import app.siakad.siakadtkadmin.presentation.screens.announcement.adapter.AnnouncementListAdater
import app.siakad.siakadtkadmin.presentation.screens.main.MainActivity
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.user.UserListFragment
import app.siakad.siakadtkadmin.presentation.utils.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class AnnouncementActivity : AppCompatActivity() {

    private val pageTitle = "Pengumuman"

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        setupAppBar()
        setupTabLayout()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@AnnouncementActivity,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        pagerAdapter.addFragment("Semua", AnnouncementListFragment.getAllAnnouncementListFragment())
        pagerAdapter.addFragment(
            "Siswa",
            AnnouncementListFragment.getUserAnnouncementListFragment()
        )
        pagerAdapter.addFragment(
            "Kelas",
            AnnouncementListFragment.getClassAnnouncementListFragment()
        )

        viewPager = findViewById(R.id.view_pager_announcement)
        viewPager.adapter = pagerAdapter

        tab = findViewById(R.id.tabs_announcement)
        tab.setupWithViewPager(viewPager)
    }
}