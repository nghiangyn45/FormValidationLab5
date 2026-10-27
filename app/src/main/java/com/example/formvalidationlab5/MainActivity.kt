package com.example.formvalidationlab5

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.formvalidationlab5.databinding.ActivityMainBinding
import com.example.formvalidationlab5.model.User
import com.example.formvalidationlab5.validator.FormValidator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Xóa lỗi thời gian thực khi gõ phím
        setupRealtimeValidation()

        // Nút Đăng ký
        binding.btnRegister.setOnClickListener {
            handleRegister()
        }

        // Nút Hủy (Xóa trắng các trường)
        binding.btnCancel.setOnClickListener {
            clearForm()
        }
    }

    private fun setupRealtimeValidation() {
        binding.etFullName.doAfterTextChanged { binding.tilFullName.error = null }
        binding.etEmail.doAfterTextChanged { binding.tilEmail.error = null }
        binding.etPhone.doAfterTextChanged { binding.tilPhone.error = null }
        binding.etMsv.doAfterTextChanged { binding.tilMsv.error = null }
        binding.etClass.doAfterTextChanged { binding.tilClass.error = null }
    }

    private fun handleRegister() {
        val name = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val msv = binding.etMsv.text.toString().trim()
        val clazz = binding.etClass.text.toString().trim()

        var isValid = true

        if (!FormValidator.isValidName(name)) {
            binding.tilFullName.error = "Họ tên không được để trống (tối thiểu 2 ký tự)"
            isValid = false
        }
        if (!FormValidator.isValidEmail(email)) {
            binding.tilEmail.error = "Email không đúng định dạng"
            isValid = false
        }
        if (!FormValidator.isValidPhone(phone)) {
            binding.tilPhone.error = "SĐT phải gồm 10 số và đúng đầu số VN"
            isValid = false
        }
        if (!FormValidator.isValidMsv(msv)) {
            binding.tilMsv.error = "Mã sinh viên không được để trống"
            isValid = false
        }
        if (!FormValidator.isValidClass(clazz)) {
            binding.tilClass.error = "Lớp không được để trống"
            isValid = false
        }

        if (isValid) {
            // Success -> Tạo đối tượng User và chuyển sang màn hình chủ/chi tiết
            val user = User(name, email, phone, msv, clazz)
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_USER", user)
            }
            startActivity(intent)
        } else {
            // Fail -> Hiển thị Snackbar báo lỗi
            Snackbar.make(binding.root, "Đăng ký thất bại! Vui lòng kiểm tra lại thông tin.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearForm() {
        binding.etFullName.text?.clear()
        binding.etEmail.text?.clear()
        binding.etPhone.text?.clear()
        binding.etMsv.text?.clear()
        binding.etClass.text?.clear()

        binding.tilFullName.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilMsv.error = null
        binding.tilClass.error = null

        Snackbar.make(binding.root, "Đã xóa trắng các trường nhập lại", Snackbar.LENGTH_SHORT).show()
    }
}