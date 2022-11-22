package com.example.hansung_teamproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.databinding.FriendBinding
import com.example.hansung_teamproject.feed.FeedActivity

class FriendListActivity : AppCompatActivity() {
    private lateinit var binding : FriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RecyclerView 테스트용 ArrayList
        val mList : ArrayList<String> = ArrayList()
        mList.add("사용자1")
        mList.add("사용자2")
        mList.add("사용자3")
        mList.add("사용자4")
        mList.add("사용자5")
        mList.add("사용자6")

        val adapter = FriendListAdapter()
        adapter.setFriendList(mList)
        binding.recyclerView3.adapter = adapter
        binding.recyclerView3.layoutManager = LinearLayoutManager(this)
        binding.recyclerView3.setHasFixedSize(true)

        //recyclerView2는 FriendItemAdpater로 해야함
        val friendRequestAdapter = FriendRequestListAdapter()
        friendRequestAdapter.setFriendList(mList)
        binding.recyclerView2.adapter = friendRequestAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.setHasFixedSize(true)

        binding.friendListBack.setOnClickListener {
            startActivity(Intent(this, FeedActivity::class.java))
        }

        binding.userSearch.setOnClickListener {
            var friendSearchDialogFragment: FriendSearchDialogFragment = FriendSearchDialogFragment()
            var bundle: Bundle = Bundle()
            bundle.putString("searchString", binding.friendSearchEditText.text.toString())
            friendSearchDialogFragment.arguments = bundle
            friendSearchDialogFragment.show(supportFragmentManager, "friendSearchDialog")
        }
    }
}