package app.siakad.siakadtkadmin.ui.registration

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.main.SectionsPagerAdapter

class RegistrationActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        viewPager = findViewById(R.id.view_pager_registration)
        tab = findViewById(R.id.tabs_registration)
    }

    private fun setupView() {
        pagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tab.setupWithViewPager(viewPager)
    }
}