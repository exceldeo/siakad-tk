package app.siakad.siakadtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class RegistrationFormActivity : AppCompatActivity() {

    private lateinit var ibtnBack: ImageButton
    private lateinit var etName: EditText
    private lateinit var etBornDate: EditText
    private lateinit var spGender: EditText
    private lateinit var spClass: EditText
    private lateinit var etParentName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etTahunAjaran: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnUploadBukti: Button
    private lateinit var etSchoolBefore: EditText
    private lateinit var btnCancel: TextView
    private lateinit var btnSimpan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_form)
        setupItemView()
    }

    private fun setupItemView() {
        ibtnBack = findViewById(R.id.ibtn_registrationform_back)
        etName = findViewById(R.id.et_registrationform_nama)
        etBornDate = findViewById(R.id.et_registrationform_ttl)
        spGender = findViewById(R.id.sp_registrationform_jenis_kelamin)
        spClass = findViewById(R.id.sp_registrationform_kelas)
        etParentName = findViewById(R.id.et_registrationform_nama_ortu)
        etAddress = findViewById(R.id.et_registrationform_alamat)
        etTahunAjaran = findViewById(R.id.sp_registrationform_tahun_ajaran)
        etPhoneNumber = findViewById(R.id.et_registrationform_no_hp_ortu)
        btnUploadBukti = findViewById(R.id.btn_registrationform_upload_bukti_bayar)
        etSchoolBefore = findViewById(R.id.et_registrationform_asal_sekolah)
        btnCancel = findViewById(R.id.btn_registrationform_batal)
        btnSimpan = findViewById(R.id.btn_registrationform_simpan)
    }
}