package app.siakad.siakadtkadmin.presentation.screens.classroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.classroom.ClassroomAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ClassroomAddActivity : AppCompatActivity() {

    private val pageTitle = "Tambah Kelas"

    private lateinit var etSelesai: TextInputLayout
    private lateinit var etMulai: TextInputLayout
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnSave: MaterialButton
    private lateinit var ddClass: TextInputLayout
    
    private lateinit var vmClassroomAdd: ClassroomAddViewModel

    var classroomType: String = ClassroomListFragment.TK_A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom_add)

        etSelesai = findViewById(R.id.et_classroom_add_selesai)
        etMulai = findViewById(R.id.et_classroom_add_mulai)

        classroomType = intent.getStringExtra(ClassroomListFragment.CLASSROOM_TYPE)!!

        setupAppBar()
        setupViewModel()
        setupDropDown()
        setupButtons()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navigateBack()
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

    private fun setupViewModel() {
        vmClassroomAdd = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(ClassroomAddViewModel::class.java)
    }

    private fun setupDropDown() {
        val menus = arrayListOf(
            ClassroomListFragment.TK_A,
            ClassroomListFragment.TK_B
        )
        val adapter = ArrayAdapter(this.applicationContext, R.layout.item_dropdown, menus)

        ddClass = findViewById(R.id.dd_classroom_add)
        (ddClass.editText as MaterialAutoCompleteTextView).setText(classroomType)
        (ddClass.editText as MaterialAutoCompleteTextView).setAdapter(adapter)
        (ddClass.editText as MaterialAutoCompleteTextView).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(str: Editable?) {
                classroomType = str.toString()
            }

            override fun beforeTextChanged(
                str: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupButtons() {
        btnCancel = findViewById(R.id.btn_classroom_add_batal)
        btnCancel.setOnClickListener {
            navigateBack()
        }

        btnSave = findViewById(R.id.btn_classroom_add_simpan)
        btnSave.setOnClickListener {
            if (validateInput()) {
                vmClassroomAdd.insertClassroom(
                    classroomType,
                    etSelesai.editText?.text.toString().toInt(),
                    etMulai.editText?.text.toString().toInt()
                )
            }
        }
    }

    private fun navigateBack() {
        startActivity(
            Intent(
                this@ClassroomAddActivity,
                ClassroomActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    private fun validateInput(): Boolean {
        var returnState = true

        if (etSelesai.editText?.text.toString().isEmpty()) {
            etSelesai.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etMulai.editText?.text.toString().isEmpty()) {
            etMulai.error = getString(R.string.empty_input)
            returnState = false
        }

        return returnState
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}