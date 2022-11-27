package com.example.hansung_teamproject.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.*
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.LoginActivity.Companion.myName
import com.example.hansung_teamproject.databinding.FeedBinding


class FeedActivity : AppCompatActivity() {


    lateinit var binding: FeedBinding
    val adapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FeedBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        println(myName)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@FeedActivity)
        binding.recyclerView.setHasFixedSize(true)


        binding.topAppBar[1].setOnClickListener {
            startActivity(Intent(this, NewPostActivity::class.java))
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.favorite -> {
                    startActivity(Intent(this, FriendListActivity::class.java))
                    true
                }
                R.id.bookmark -> {
                    startActivity(Intent(this, BookmarkActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
