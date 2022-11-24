package com.example.hansung_teamproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.databinding.FriendFeedBinding

class FriendFeedActivity : AppCompatActivity() {
    private lateinit var binding: FriendFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FriendFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.friendFeedNameTextView.text = intent?.getStringExtra("name")
        binding.friendFeedEmailTextView.text = intent?.getStringExtra("email")


        binding.friendFeedRecyclerView.adapter = FriendFeedAdapter(binding.friendFeedEmailTextView.text.toString(), binding.friendFeedNameTextView.text.toString())
        binding.friendFeedRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.friendFeedRecyclerView.setHasFixedSize(true)
    }
}