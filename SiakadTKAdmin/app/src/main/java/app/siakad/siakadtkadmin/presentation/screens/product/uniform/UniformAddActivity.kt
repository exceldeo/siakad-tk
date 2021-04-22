package app.siakad.siakadtkadmin.presentation.screens.product.uniform

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.domain.models.product.DetailSeragamModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.uniform.UniformAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.product.ProductListActivity
import app.siakad.siakadtkadmin.presentation.screens.product.uniform.adapter.UniformSizeListAdapter
import app.siakad.siakadtkadmin.presentation.screens.product.uniform.dialog.UniformProductDialog
import app.siakad.siakadtkadmin.presentation.screens.product.uniform.dialog.UniformProductListener
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class UniformAddActivity : AppCompatActivity(), AlertListener, UniformProductListener {

    private val pageTitle = "Tambah Produk"

    private lateinit var etName: EditText
    private lateinit var ddGender: TextInputLayout
    private lateinit var ivPhotoPreview: ImageView

    private lateinit var btnAddPhoto: RelativeLayout
    private lateinit var btnAddData: RelativeLayout
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnSave: MaterialButton

    private lateinit var imgBtnAddPhoto: LinearLayout
    private lateinit var dialogUniform: UniformProductDialog
    private lateinit var rvUniformSize: RecyclerView

    private lateinit var vmUniformAdd: UniformAddViewModel
    private lateinit var uniformSizeAdapter: UniformSizeListAdapter

    private val uniformSizeList = arrayListOf<DetailSeragamModel>()
    private var uniformGender = GENDER_MAN
    private var uniformCount = 0
    private var uniformImage: Uri? = null

    companion object {
        const val GENDER_MAN = "Laki-laki"
        const val GENDER_WOMAN = "Perempuan"

        const val PICK_PHOTO_REQUEST = 1000
        const val PERMISSION_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uniform_add)

        etName = findViewById(R.id.et_uniform_add_nama)
        ivPhotoPreview = findViewById(R.id.iv_uniform_add_foto_preview)
        imgBtnAddPhoto = findViewById(R.id.btn_uniform_add_add_foto)

        setupAppBar()
        setupViewModel()
        setupButtons()
        setupDropDown()
        setupListAdapter()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Akses ditolak", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_PHOTO_REQUEST) {
            val imageUri: Uri = data?.data!!
            ivPhotoPreview.setImageURI(imageUri)
            imgBtnAddPhoto.visibility = View.GONE
            uniformImage = imageUri
        }
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

    private fun navigateBack() {
        val intent = Intent(
            this@UniformAddActivity,
            ProductListActivity::class.java
        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(ProductListActivity.PAGE_TYPE, ProductListActivity.UNIFORM_PAGE)
        startActivity(intent)
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        vmUniformAdd = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(UniformAddViewModel::class.java)
    }

    private fun setupButtons() {
        btnAddPhoto = findViewById(R.id.iv_uniform_add_foto)
        btnAddPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(
                        permissions,
                        PERMISSION_REQUEST
                    );
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }

        btnCancel = findViewById(R.id.btn_uniform_add_batal)
        btnCancel.setOnClickListener {
            navigateBack()
        }

        btnSave = findViewById(R.id.btn_uniform_add_simpan)
        btnSave.setOnClickListener {
            if (validateInptu()) {
                insertUniform()
            }
        }

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
            override fun afterTextChanged(str: Editable?) {
                uniformGender = str.toString()
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            PICK_PHOTO_REQUEST
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun validateInptu(): Boolean {
        var returnState = true

        if (etName.text.isEmpty()) {
            etName.error = getString(R.string.empty_input)
            returnState = false
        }

        if (uniformImage == null) {
            val alertDialog = AlertDialogFragment(
                "Foto belum ditambahkan!",
                "Apakah Anda yakin menyimpan data tanpa menggunakan foto?"
            )
            alertDialog.show(supportFragmentManager, null)
            returnState = false
        }

        return returnState
    }

    override fun alertAction() {
        insertUniform()
    }

    private fun insertUniform() {
        vmUniformAdd.insertUniform(
            uniformGender,
            etName.text.toString(),
            uniformImage,
            uniformCount,
            uniformSizeList
        )
    }

    override fun insertData(ukuran: String, jumlah: Int, harga: Int) {
        uniformCount += jumlah
        uniformSizeList.add(DetailSeragamModel(ukuran = ukuran, jumlah = jumlah, harga = harga))
        uniformSizeAdapter.addData(
            DetailSeragamModel(ukuran = ukuran, jumlah = jumlah, harga = harga)
        )
    }

    private fun setupListAdapter() {
        rvUniformSize = findViewById(R.id.rv_uniform_add_daftar_ukuran)
        uniformSizeAdapter = UniformSizeListAdapter()
        rvUniformSize.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = uniformSizeAdapter
        }
    }
}