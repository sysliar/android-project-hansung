package com.example.hansung_teamproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hansung_teamproject.databinding.BookmarkItemBinding
import com.example.hansung_teamproject.feed.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    private var postList: ArrayList<Post> = arrayListOf()

    inner class ViewHolder(private val binding: BookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            val storage = Firebase.storage
            val pathReference =
                storage.reference.child("upload/" + postList[pos].img_url).downloadUrl
            pathReference.addOnSuccessListener { uri ->
                Glide.with(itemView.context)
                    .load(uri).centerCrop()
                    .into(binding.bookmarkFeedImage)

            }
            binding.bookmarkContent.text = postList[pos].content
            binding.bookmarkUserName.text = postList[pos].name
            binding.bookmarkTimestampTextView.text =
                postList[pos].timestamp.toDate().toLocaleString()
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
        val binding = BookmarkItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

}