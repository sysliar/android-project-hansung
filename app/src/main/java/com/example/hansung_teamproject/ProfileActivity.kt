package com.example.hansung_teamproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hansung_teamproject.databinding.FeedBinding
import com.example.hansung_teamproject.databinding.ProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}