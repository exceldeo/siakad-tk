package app.siakad.siakadtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {

    private lateinit var ibtnBack: ImageButton
    private lateinit var civProfileImg: CircleImageView
    private lateinit var btnChangeProfileImg: TextView
    private lateinit var etName: EditText
    private lateinit var etClass: EditText
    private lateinit var etTahunAjaran: EditText
    private lateinit var etGender: EditText
    private lateinit var etBornDate: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etParentName: EditText
    private lateinit var etSchoolBefore: EditText
    private lateinit var btnCancel: TextView
    private lateinit var btnSimpan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupItemView()
    }

    private fun setupItemView() {
        ibtnBack = findViewById(R.id.ibtn_aprofile_back)
        civProfileImg = findViewById(R.id.civ_aprofile_user_img)
        btnChangeProfileImg = findViewById(R.id.btn_aprofile_ubah_foto)
        etName = findViewById(R.id.et_aprofile_nama)
        etClass = findViewById(R.id.et_aprofile_kelas)
        etTahunAjaran = findViewById(R.id.et_aprofile_tahunajaran)
        etGender = findViewById(R.id.et_aprofile_jeniskelamin)
        etBornDate = findViewById(R.id.et_aprofile_ttl)
        etAddress = findViewById(R.id.et_aprofile_alamat)
        etPhoneNumber = findViewById(R.id.et_aprofile_no_hp_ortu)
        etParentName = findViewById(R.id.et_aprofile_nama_ortu)
        etSchoolBefore = findViewById(R.id.et_aprofile_asal_sekolah)
        btnCancel = findViewById(R.id.btn_aprofile_batal)
        btnSimpan = findViewById(R.id.btn_aprofile_simpan)
    }


}