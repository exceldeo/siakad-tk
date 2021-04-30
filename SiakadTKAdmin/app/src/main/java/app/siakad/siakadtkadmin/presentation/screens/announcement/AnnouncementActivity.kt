package app.siakad.siakadtkadmin.presentation.screens.announcement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.PengumumanModel
import app.siakad.siakadtkadmin.presentation.screens.announcement.listener.AnnouncementEditListener
import app.siakad.siakadtkadmin.presentation.screens.main.MainActivity
import app.siakad.siakadtkadmin.presentation.utils.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class AnnouncementActivity : AppCompatActivity(), AnnouncementEditListener {

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

    override fun navigateToAnnouncementEdit(announecement: PengumumanModel) {
        val intent = Intent(this, AnnouncementAddActivity::class.java)
        intent.putExtra(AnnouncementAddActivity.ANNOUNCEMENT_EDIT, announecement)
        startActivity(intent)
    }
}