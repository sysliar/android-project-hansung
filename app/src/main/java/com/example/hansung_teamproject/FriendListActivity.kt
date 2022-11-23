package com.example.hansung_teamproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.databinding.FriendBinding
import com.example.hansung_teamproject.feed.FeedActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendListActivity : AppCompatActivity() {
    private lateinit var binding: FriendBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val friendList: ArrayList<Friend> = arrayListOf()
        val friendListAdapter = FriendListAdapter()
        db.collection("users/${myEmail}/friend").get().addOnSuccessListener { documents ->
            for (document in documents) {
                println(document.data["name"])
                friendList.add(Friend(document.data["name"] as String, document.data["email"] as String))
            }
            friendListAdapter.setFriendList(friendList)
        }
        binding.friendListRecyclerView.adapter = friendListAdapter
        binding.friendListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.friendListRecyclerView.setHasFixedSize(true)


        val friendRequestList: ArrayList<Friend> = arrayListOf()
        val friendRequestAdapter = FriendRequestListAdapter()
        db.collection("users/${myEmail}/friend_request").get().addOnSuccessListener { documents ->
            for (document in documents) {
                println(document.data["name"])
                friendRequestList.add(Friend(document.data["name"] as String, document.data["email"] as String))
            }
            friendRequestAdapter.setFriendList(friendRequestList)
        }
        binding.friendRequestListRecyclerView.adapter = friendRequestAdapter
        binding.friendRequestListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.friendRequestListRecyclerView.setHasFixedSize(true)

        binding.friendListBack.setOnClickListener {
            startActivity(Intent(this, FeedActivity::class.java))
        }

        binding.userSearch.setOnClickListener {
            var friendSearchDialogFragment: FriendSearchDialogFragment =
                FriendSearchDialogFragment()
            var bundle: Bundle = Bundle()
            bundle.putString("searchString", binding.friendSearchEditText.text.toString())
            friendSearchDialogFragment.arguments = bundle
            friendSearchDialogFragment.show(supportFragmentManager, "friendSearchDialog")
        }
    }
}