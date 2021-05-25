package app.siakad.siakadtk.presentation.screens.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.Pengguna
import app.siakad.siakadtk.infrastructure.viewmodels.screens.main.profile.ProfileViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtk.presentation.views.alert.AlertListener

class EmailActivity : AppCompatActivity(), AlertListener {
    private val pageTitle = "Ubah Email"

    private lateinit var etOldEmail: EditText
    private lateinit var etNewEmail: EditText
    private lateinit var etConfirmPassword: EditText

    private lateinit var btnSave: Button

    private lateinit var vmProfile : ProfileViewModel

    private var dataUser = Pengguna()

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)
        setupItemView()
        setupView()
    }

    private fun setupView() {
        setupAppBar()

        btnSave.setOnClickListener {
            val alertDialog = AlertDialogFragment(
                "Konfirmasi ubah email",
                "Apakah Anda yakin mengubah email?"
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
    }

    private fun validateForm(): Boolean {
        var valid = true

        val oldEmail = etOldEmail.text.toString()
        if (TextUtils.isEmpty(oldEmail)) {
            etOldEmail.error = getString(R.string.empty_input)
            valid = false
        } else if (oldEmail != dataUser.email) {
            etOldEmail.error = getString(R.string.err_conf_old_email)
            valid = false
        }

        val newEmail = etNewEmail.text.toString()
        if (TextUtils.isEmpty(newEmail)) {
            etNewEmail.error = getString(R.string.empty_input)
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            etNewEmail.error = getString(R.string.wrong_email)
            valid = false
        }

        val password = etConfirmPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            etConfirmPassword.error = getString(R.string.empty_input)
            valid = false
        } else if (password != dataUser.passwd) {
            etConfirmPassword.error = getString(R.string.err_conf_passwd)
            valid = false
        }

        return valid
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)

        etOldEmail = findViewById(R.id.et_email_email_lama)
        etNewEmail = findViewById(R.id.et_email_email_baru)
        etConfirmPassword = findViewById(R.id.et_email_konfirmasi_password)
        btnSave = findViewById(R.id.btn_password_save)
    }

    override fun alertAction(tag: String?) {
        if(validateForm()) {
            vmProfile.updateEmail(dataUser, etNewEmail.text.toString())
            navigateBack()
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@EmailActivity,
                SettingsActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}