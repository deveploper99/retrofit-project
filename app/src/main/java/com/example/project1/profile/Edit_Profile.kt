package com.example.project1.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.R
import com.example.project1.databinding.ActivityEditProfileBinding

class Edit_Profile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrow.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }



        binding.saveButton.setOnClickListener {
            if (validateInputs()) {
                Toast.makeText(this, "Profile Saved Successfully", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Name validation
        val name = binding.nameFild.text.toString().trim()
        if (name.isEmpty()) {
            binding.nameFild.error = "Name is required"
            isValid = false
        } else {
            binding.nameFild.error = null
        }

        // Username validation
        val username = binding.userFild.text.toString().trim()
        if (username.isEmpty()) {
            binding.userFild.error = "Username is required"
            isValid = false
        } else {
            binding.userFild.error = null
        }

        // Phone validation
        val phone = binding.phoneNumber.text.toString().trim()
        if (phone.isEmpty()) {
            binding.phoneNumber.error = "Phone number is required"
            isValid = false
        } else if (phone.length != 11 || !phone.all { it.isDigit() }) {
            binding.phoneNumber.error = "Enter valid 10-digit phone number"
            isValid = false
        } else {
            binding.phoneNumber.error = null
        }

        // Email validation
        val email = binding.emailId.text.toString().trim()
        if (email.isEmpty()) {
            binding.emailId.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailId.error = "Enter valid email"
            isValid = false
        } else {
            binding.emailId.error = null
        }

        return isValid
    }

    }

