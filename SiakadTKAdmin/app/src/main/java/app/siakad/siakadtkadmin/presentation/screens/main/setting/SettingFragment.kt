package app.siakad.siakadtkadmin.presentation.screens.main.setting

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
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.main.setting.SettingViewModel
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import com.google.android.material.button.MaterialButton

class SettingFragment : Fragment() {

  @SuppressLint("UseSwitchCompatOrMaterialCode")
  private lateinit var swtchRegistration: Switch

  @SuppressLint("UseSwitchCompatOrMaterialCode")
  private lateinit var swtchOrder: Switch

  private lateinit var btnKeluar: MaterialButton
  private lateinit var btnReset: MaterialButton

  private lateinit var vmSetting: SettingViewModel

  companion object {
    const val TAG_LOGOUT = "Keluar Akun"
    const val TAG_RESET = "Reset Daful"
  }

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
      btnKeluar = v.findViewById(R.id.btn_setting_keluar)
      btnReset = v.findViewById(R.id.btn_setting_reset_daful)
    }

    vmSetting = ViewModelProvider(this).get(SettingViewModel::class.java)
  }

  private fun setupView() {
    btnKeluar.setOnClickListener {
      val alertDialog: AlertDialogFragment =
        AlertDialogFragment("Keluar", "Apakah Anda yakin ingin keluar?")
      alertDialog.show(this@SettingFragment.parentFragmentManager, TAG_LOGOUT)
    }

    btnReset.setOnClickListener {
      val alertDialog: AlertDialogFragment =
        AlertDialogFragment("Reset Daftar Ulang", "Apakah Anda yakin ingin mereset daftar ulang semua siswa?")
      alertDialog.show(this@SettingFragment.parentFragmentManager, TAG_RESET)
    }
  }
}