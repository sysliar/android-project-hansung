package com.example.hansung_teamproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.databinding.BookmarkBinding
import com.example.hansung_teamproject.feed.FeedActivity
import com.example.hansung_teamproject.feed.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding : BookmarkBinding
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var postIdList: ArrayList<String> = arrayListOf()
        var postList: ArrayList<Post> = arrayListOf()
        val adapter = BookmarkAdapter()
        firestore?.collection("users/${myEmail}/bookmark")?.get()?.addOnSuccessListener { result ->
            for (document in result) {
                postIdList.add(document.data["documentId"].toString())
            }
            for (postId in postIdList) {
                firestore?.collection("posts")?.document(postId)?.get()?.addOnSuccessListener { document ->
                    postList.add(document.toObject<Post>()!!)
                    println(postList)
                    adapter.setPostList(postList)
                }
            }
        }?.addOnFailureListener { exception ->
            Log.w("TAG", "Error getting documents: ", exception)
        }


        binding.bookmarkRecyclerView.adapter = adapter
        binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookmarkRecyclerView.setHasFixedSize(true)

        binding.bookmarkBack.setOnClickListener {
            startActivity(Intent(this, FeedActivity::class.java))
        }
    }
}