package com.example.hansung_teamproject.feed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.FriendListActivity
import com.example.hansung_teamproject.NewPostActivity
import com.example.hansung_teamproject.R
import com.example.hansung_teamproject.databinding.FeedBinding


class FeedActivity : AppCompatActivity() {
    lateinit var binding: FeedBinding
    val adapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FeedBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@FeedActivity)
        binding.recyclerView.setHasFixedSize(true)

        binding.topAppBar[1].setOnClickListener {
            startActivity(Intent(this, NewPostActivity::class.java))
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.favorite -> {
                    startActivity(Intent(this, FriendListActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
