package app.siakad.siakadtkadmin.presentation.screens.product.book

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.viewmodels.screens.product.book.BookAddViewModel
import app.siakad.siakadtkadmin.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtkadmin.presentation.screens.product.ProductListActivity
import app.siakad.siakadtkadmin.presentation.screens.product.uniform.UniformAddActivity
import app.siakad.siakadtkadmin.presentation.views.alert.AlertDialogFragment
import app.siakad.siakadtkadmin.presentation.views.alert.AlertListener
import com.google.android.material.button.MaterialButton

class BookAddActivity : AppCompatActivity(), AlertListener {

    private val pageTitle = "Tambah Produk"

    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etNum: EditText

    private lateinit var btnAddPhoto: RelativeLayout
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnSave: MaterialButton

    private lateinit var ivPhotoPreview: ImageView
    private lateinit var imgBtnAddPhoto: LinearLayout

    private lateinit var vmBookAdd: BookAddViewModel

    private var bookImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)

        etName = findViewById(R.id.et_book_add_nama)
        etPrice = findViewById(R.id.et_book_add_harga)
        etNum = findViewById(R.id.et_book_add_jumlah)
        ivPhotoPreview = findViewById(R.id.iv_book_add_foto_preview)
        imgBtnAddPhoto = findViewById(R.id.btn_book_add_add_foto)

        setupAppBar()
        setupButtons()
        setupViewModel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            UniformAddActivity.PERMISSION_REQUEST -> {
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

        if (resultCode == Activity.RESULT_OK && requestCode == UniformAddActivity.PICK_PHOTO_REQUEST) {
            val imageUri: Uri = data?.data!!
            ivPhotoPreview.setImageURI(imageUri)
            imgBtnAddPhoto.visibility = View.GONE
            bookImage = imageUri
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            UniformAddActivity.PICK_PHOTO_REQUEST
        )
    }

    private fun navigateBack() {
        val intent = Intent(
            this@BookAddActivity,
            ProductListActivity::class.java
        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(ProductListActivity.PAGE_TYPE, ProductListActivity.BOOK_PAGE)
        startActivity(intent)
    }

    private fun setupButtons() {
        btnAddPhoto = findViewById(R.id.iv_book_add_foto)
        btnAddPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(
                        permissions,
                        UniformAddActivity.PERMISSION_REQUEST
                    );
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }

        btnCancel = findViewById(R.id.btn_book_add_batal)
        btnCancel.setOnClickListener {
            navigateBack()
        }

        btnSave = findViewById(R.id.btn_book_add_simpan)
        btnSave.setOnClickListener {
            if (validateInput()) {
                insertBook()
            }
        }
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        vmBookAdd = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(BookAddViewModel::class.java)
    }

    private fun validateInput(): Boolean {
        var returnState = true

        if (etName.text.isEmpty()) {
            etName.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etNum.text.isEmpty()) {
            etNum.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etPrice.text.isEmpty()) {
            etPrice.error = getString(R.string.empty_input)
            returnState = false
        }

        if (bookImage == null) {
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
        insertBook()
    }

    private fun insertBook() {
        vmBookAdd.insertBook(
            etName.text.toString(),
            bookImage,
            etNum.text.toString().toInt(),
            etPrice.text.toString().toInt()
        )
    }
}