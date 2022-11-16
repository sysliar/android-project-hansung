package com.example.hansung_teamproject.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.R
import com.example.hansung_teamproject.databinding.FeedBinding
import com.example.hansung_teamproject.databinding.LoginBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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

    }
}
