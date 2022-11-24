package com.example.hansung_teamproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hansung_teamproject.databinding.FeedBinding
import com.example.hansung_teamproject.databinding.FeedItemBinding
import com.example.hansung_teamproject.feed.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendFeedAdapter(email: String, name: String) : RecyclerView.Adapter<FriendFeedAdapter.ViewHolder>() {
    private var postList: ArrayList<Post> = arrayListOf()
    var firestore : FirebaseFirestore? = null
    init {
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("posts")?.whereEqualTo("email", email)
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                postList.clear()
                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(Post::class.java)
                    postList.add(item!!)
                }
                notifyDataSetChanged()
            }
    }

    inner class ViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            binding.content.text = postList[pos].content
            binding.userName.text = postList[pos].name
        }
    }

    fun setPostList(list : ArrayList<Post>) {
        postList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FeedItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

}