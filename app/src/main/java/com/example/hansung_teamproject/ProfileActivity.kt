package com.example.hansung_teamproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.LoginActivity.Companion.myBirth
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.LoginActivity.Companion.myName
import com.example.hansung_teamproject.databinding.ProfileBinding
import com.example.hansung_teamproject.feed.FeedActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView5.text = myName
        binding.textView6.text = myBirth
        binding.textView8.text = myEmail

        binding.profileRecyclerView.adapter = MyFeedAdapter()
        binding.profileRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.profileRecyclerView.setHasFixedSize(true)

        binding.imageButton10.setOnClickListener {
            startActivity(Intent(this, FeedActivity::class.java))
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, ProfileSetUpActivity::class.java))
        }

        binding.button2.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(
                baseContext, "로그아웃 하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, LoginActivity::class.java))
            //finish()
        }
    }
}