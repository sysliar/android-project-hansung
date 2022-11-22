package com.example.hansung_teamproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hansung_teamproject.databinding.FriendListBinding

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {
    private lateinit var mFriendList : ArrayList<String>

    fun setFriendList(list: ArrayList<String>) {
        mFriendList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: FriendListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with(mFriendList) {
                binding.friendListName.text = mFriendList[pos]
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
            //TODO - 친구 삭제
        }

        binding.root.setOnClickListener {
            //TODO - 친구 글 모아보기
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

}