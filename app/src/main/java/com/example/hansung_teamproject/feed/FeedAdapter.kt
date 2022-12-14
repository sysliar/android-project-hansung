package com.example.hansung_teamproject.feed

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hansung_teamproject.LoginActivity
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.databinding.FeedBinding
import com.example.hansung_teamproject.databinding.FeedItemBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    private var postList: ArrayList<Post> = arrayListOf()
    var firestore: FirebaseFirestore? = null

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("posts")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                postList.clear()
                for (snapshot in querySnapshot!!.documents) {
//                    var item = snapshot.toObject(Post::class.java)
                    var item: Post = Post(
                        snapshot["name"].toString(),
                        snapshot["email"].toString(),
                        snapshot["content"].toString(),
                        snapshot["like"] as Long,
                        snapshot["img_url"].toString(),
                        snapshot["timestamp"] as Timestamp,
                        snapshot.id
                    )
                    postList.add(item!!)
                }
                postList.sortByDescending { it.timestamp }
                notifyDataSetChanged()
            }
    }

    inner class ViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            val storage = Firebase.storage
            val pathReference =
                storage.reference.child("upload/" + postList[pos].img_url).downloadUrl
            pathReference.addOnSuccessListener { uri ->
                Glide.with(itemView.context)
                    .load(uri).centerCrop()
                    .into(binding.feedImage)

            }
            binding.timestampTextView.text = postList[pos].timestamp.toDate().toLocaleString()
            binding.content.text = postList[pos].content
            binding.userName.text = postList[pos].name
            binding.postIdTextView.text = postList[pos].id
        }
    }

    fun setPostList(list: ArrayList<Post>) {
        postList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FeedItemBinding.inflate(layoutInflater, parent, false)
        binding.bookmarkButton.setOnClickListener {
            firestore?.collection("users/${myEmail}/bookmark")
                ?.add(hashMapOf("documentId" to binding.postIdTextView.text))?.addOnSuccessListener {
                    Toast.makeText(
                        parent.context, "????????? ?????? ??????",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

}