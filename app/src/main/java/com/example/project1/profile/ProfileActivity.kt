package com.example.project1.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.MainActivity
import com.example.project1.R
import com.example.project1.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.backArrow.setOnClickListener {
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
       }

        binding.imageView2.setOnClickListener {
            val intent = Intent(this, Edit_Profile::class.java)
            startActivity(intent)
        }


    }
}