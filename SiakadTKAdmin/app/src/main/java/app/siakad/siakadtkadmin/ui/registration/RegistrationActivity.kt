package app.siakad.siakadtkadmin.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.main.MainActivity
import app.siakad.siakadtkadmin.ui.main.SectionsPagerAdapter

class RegistrationActivity : AppCompatActivity() {

    private val pageTitle = "Daftar Ulang"

    private lateinit var toolbar: Toolbar
    private lateinit var pagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setupItemView()
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        viewPager = findViewById(R.id.view_pager_registration)
        tab = findViewById(R.id.tabs_registration)
    }

    private fun setupView() {
        setupAppBar()

        pagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tab.setupWithViewPager(viewPager)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}