package app.siakad.siakadtk.presentation.screens.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile.ProfileViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.registration.RegistrationActivity
import app.siakad.siakadtk.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtk.presentation.views.alert.AlertListener
import com.squareup.picasso.Picasso

class PasswordActivity : AppCompatActivity(), AlertListener {
    private val pageTitle = "Ubah Password"

    private lateinit var etOldPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmNewPassword: EditText

    private lateinit var btnSave: Button

    private lateinit var vmProfile : ProfileViewModel

    private var dataUser = Pengguna()

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)

        etOldPassword = findViewById(R.id.et_password_password_lama)
        etNewPassword = findViewById(R.id.et_password_password_baru)
        etConfirmNewPassword = findViewById(R.id.et_password_konfirmasi_password_baru)
        btnSave = findViewById(R.id.btn_password_save)
    }

    private fun setupView() {
        setupAppBar()

        btnSave.setOnClickListener {
            val alertDialog = AlertDialogFragment(
                "Konfirmasi ubah password",
                "Apakah Anda yakin mengubah password?"
            )
            alertDialog.show(supportFragmentManager, null)
        }

        vmProfile = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(ProfileViewModel::class.java)

        val obsProfileGetUser = Observer<Pengguna> {
            dataUser = it
        }

        vmProfile.getUserData()
            .observe(this, obsProfileGetUser)

        etOldPassword.transformationMethod = PasswordTransformationMethod()
        etNewPassword.transformationMethod = PasswordTransformationMethod()
        etConfirmNewPassword.transformationMethod = PasswordTransformationMethod()
    }

    private fun validateForm(): Boolean {
        var valid = true

        val oldPassword = etOldPassword.text.toString()
        if (TextUtils.isEmpty(oldPassword)) {
            etOldPassword.error = getString(R.string.empty_input)
            valid = false
        } else if (oldPassword != dataUser.passwd) {
            etOldPassword.error = getString(R.string.err_conf_old_passwd)
            valid = false
        }

        val password = etNewPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            etNewPassword.error = getString(R.string.empty_input)
            valid = false
        } else if (password.length < 6) {
            etNewPassword.error = getString(R.string.weak_passwd)
            valid = false
        }

        val repeatPassword = etConfirmNewPassword.text.toString()
        if (TextUtils.isEmpty(repeatPassword)) {
            etConfirmNewPassword.error = getString(R.string.empty_input)
            valid = false
        } else if (repeatPassword != etNewPassword.text.toString()) {
            etConfirmNewPassword.error = getString(R.string.err_conf_passwd)
            valid = false
        }

        return valid
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun alertAction(tag: String?) {
        if(validateForm()) {
            vmProfile.updatePassword(dataUser, etNewPassword.text.toString())
        }
        navigateBack()
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@PasswordActivity,
                SettingsActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}