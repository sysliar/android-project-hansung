package com.example.hansung_teamproject

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.databinding.FriendListBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {
    var mFriendList : ArrayList<Friend> = arrayListOf()
    val db = Firebase.firestore
    fun setFriendList(list: ArrayList<Friend>) {
        mFriendList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: FriendListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with(mFriendList) {
                binding.friendListName.text = mFriendList[pos].name
                binding.friendEmailTextView.text = mFriendList[pos].email
            }
        }
    }

    override fun getItemCount(): Int {
        return mFriendList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FriendListBinding.inflate(layoutInflater, parent, false)
        binding.friendListRemove.setOnClickListener {
            db.collection("users/${myEmail}/friend").document(binding.friendEmailTextView.text.toString()).delete()
            db.collection("users/${binding.friendEmailTextView.text.toString()}/friend").document(
                myEmail).delete()
            mFriendList.remove(Friend(binding.friendListName.text.toString(), binding.friendEmailTextView.text.toString()))
            notifyDataSetChanged()
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent: Intent = Intent(holder.itemView?.context, FriendFeedActivity::class.java)
            intent.putExtra("name", mFriendList[position].name)
            intent.putExtra("email", mFriendList[position].email)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
        holder.setContents(position)
    }

}