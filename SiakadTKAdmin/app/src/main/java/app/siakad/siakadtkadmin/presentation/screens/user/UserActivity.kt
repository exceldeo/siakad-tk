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

    private lateinit var toolbar: Toolbar
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        viewPager = findViewById(R.id.view_pager_user)
        tab = findViewById(R.id.tabs_user)
    }

    private fun setupView() {
        setupAppBar()

        pagerAdapter =
            ViewPagerAdapter(
                this,
                supportFragmentManager
            )
        viewPager.adapter = pagerAdapter
        tab.setupWithViewPager(viewPager)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}