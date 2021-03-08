package app.siakad.siakadtkadmin.presentation.main.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.viewmodels.main.setting.SettingViewModel
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener

class SettingFragment : Fragment() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swtchRegistration: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swtchOrder: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swtchPengumuman: Switch
    private lateinit var btnKeluar: CardView

    private lateinit var alertDialog: AlertDialogFragment

    private lateinit var vmSetting: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        setupItemView(view)
        setupView()
        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            swtchRegistration = v.findViewById(R.id.swtch_setting_daful)
            swtchOrder = v.findViewById(R.id.swtch_setting_pemesanan)
            swtchPengumuman = v.findViewById(R.id.swtch_setting_pengumuman)
            btnKeluar = v.findViewById(R.id.btn_setting_keluar)
        }

        alertDialog = AlertDialogFragment("Keluar", "Apakah Anda yakin ingin keluar?")

        vmSetting =
            ViewModelProvider(this).get(SettingViewModel::class.java)
    }

    private fun setupView() {
        btnKeluar.setOnClickListener {
            alertDialog.show(this@SettingFragment.parentFragmentManager, null)
        }
    }
}