package app.siakad.siakadtkadmin.presentation.screens.user

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.data.Siswa
import app.siakad.siakadtkadmin.presentation.screens.main.MainActivity
import app.siakad.siakadtkadmin.presentation.screens.user.detail.unverified.UserDetailUnverActivity
import app.siakad.siakadtkadmin.presentation.screens.user.detail.verified.UserDetailActivity
import app.siakad.siakadtkadmin.presentation.screens.user.listener.UserClickListener
import app.siakad.siakadtkadmin.presentation.utils.adapter.ViewPagerAdapter

class UserActivity : AppCompatActivity(), UserClickListener {

    private val pageTitle = "Pengguna"

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setupAppBar()

        setupTabLayout()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@UserActivity,
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
        pagerAdapter.addFragment("Terverifikasi", UserListFragment.getUserVerifiedListFragment())
        pagerAdapter.addFragment("Belum Diverifikasi", UserListFragment.getUserUnverifiedListFragment())

        viewPager = findViewById(R.id.view_pager_user)
        viewPager.adapter = pagerAdapter

        tab = findViewById(R.id.tabs_user)
        tab.setupWithViewPager(viewPager)
    }

    override fun navigateToUserDetail(siswa: Siswa) {
        if (siswa.status) {
            val intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra(UserListFragment.VERIFIED_USER, siswa)
            startActivity(intent)
        } else {
            val intent = Intent(this, UserDetailUnverActivity::class.java)
            intent.putExtra(UserListFragment.UNVERIFIED_USER, siswa)
            startActivity(intent)
        }
    }
}