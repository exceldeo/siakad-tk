package app.siakad.siakadtkadmin.presentation.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import app.siakad.siakadtkadmin.R

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var swtchRegistration: Switch
    private lateinit var swtchOrder: Switch
    private lateinit var swtchPengumuman: Switch

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        setupItemView(view)
        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            swtchRegistration = v.findViewById(R.id.swtch_setting_daful)
            swtchOrder = v.findViewById(R.id.swtch_setting_pemesanan)
            swtchPengumuman = v.findViewById(R.id.swtch_setting_pengumuman)
        }

        settingViewModel =
            ViewModelProviders.of(this).get(SettingViewModel::class.java)
    }
}