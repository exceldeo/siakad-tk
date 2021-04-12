package app.siakad.siakadtkadmin.presentation.screens.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.announcement.AnnouncementListFragment
import app.siakad.siakadtkadmin.presentation.screens.product.dialog.UniformProductDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class UniformAddActivity : AppCompatActivity() {

    private val pageTitle = "Tambah Produk"

    private lateinit var etName: EditText
    private lateinit var ddGender: TextInputLayout
    private lateinit var btnAddPhoto: RelativeLayout
    private lateinit var btnAddData: RelativeLayout
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnSave: MaterialButton
    private lateinit var dialogUniform: UniformProductDialog

    companion object {
        const val GENDER_MAN = "Laki-laki"
        const val GENDER_WOMAN = "Perempuan"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uniform_add)

        etName = findViewById(R.id.et_uniform_add_nama)

        setupAppBar()
        setupButtons()
        setupDropDown()
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupButtons() {
        btnAddPhoto = findViewById(R.id.iv_uniform_add_foto)
        btnCancel = findViewById(R.id.btn_uniform_add_batal)
        btnSave = findViewById(R.id.btn_uniform_add_simpan)

        dialogUniform = UniformProductDialog()
        btnAddData = findViewById(R.id.btn_uniform_add_tambah_ukuran)
        btnAddData.setOnClickListener {
            dialogUniform.show(supportFragmentManager, null)
        }
    }

    private fun setupDropDown() {
        val menus = arrayListOf(
            GENDER_MAN,
            GENDER_WOMAN
        )
        val adapter = ArrayAdapter(this.applicationContext, R.layout.item_dropdown, menus)

        ddGender = findViewById(R.id.dd_uniform_add)
        (ddGender.editText as MaterialAutoCompleteTextView).setText(GENDER_MAN)
        (ddGender.editText as MaterialAutoCompleteTextView).setAdapter(adapter)
        (ddGender.editText as MaterialAutoCompleteTextView).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(str: Editable?) {}

            override fun beforeTextChanged(
                str: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}