package app.siakad.siakadtkadmin.presentation.screens.classroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.KelasModel
import app.siakad.siakadtkadmin.infrastructure.data.Kelas
import app.siakad.siakadtkadmin.presentation.screens.main.MainActivity
import app.siakad.siakadtkadmin.presentation.screens.classroom.ClassroomListFragment
import app.siakad.siakadtkadmin.presentation.screens.classroom.detail.ClassroomDetailActivity
import app.siakad.siakadtkadmin.presentation.screens.classroom.listener.ClassroomClickListener
import app.siakad.siakadtkadmin.presentation.utils.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class ClassroomActivity : AppCompatActivity(), ClassroomClickListener {

    private val pageTitle = "Kelas"

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom)

        setupAppBar()
        setupTabLayout()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@ClassroomActivity,
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
        pagerAdapter.addFragment(
            ClassroomListFragment.TK_A,
            ClassroomListFragment.getTKAListFragment()
        )
        pagerAdapter.addFragment(
            ClassroomListFragment.TK_B,
            ClassroomListFragment.getTKBListFragment()
        )

        viewPager = findViewById(R.id.view_pager_classroom)
        viewPager.adapter = pagerAdapter

        tab = findViewById(R.id.tabs_classroom)
        tab.setupWithViewPager(viewPager)
    }

    override fun navigateToClassroomDetail(kelas: KelasModel) {
        val intent = Intent(this, ClassroomDetailActivity::class.java)
        startActivity(intent)
    }
}