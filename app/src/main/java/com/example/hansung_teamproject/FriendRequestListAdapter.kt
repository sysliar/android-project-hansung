package com.example.hansung_teamproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hansung_teamproject.databinding.FriendItemBinding
import com.example.hansung_teamproject.databinding.FriendListBinding

class FriendRequestListAdapter : RecyclerView.Adapter<FriendRequestListAdapter.ViewHolder>() {
    private lateinit var friendRequestList: ArrayList<String>

    fun setFriendList(list: ArrayList<String>) {
        friendRequestList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: FriendItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with(friendRequestList) {
                binding.nameTextView.text = friendRequestList[pos]
            }
        }
    }

    override fun getItemCount(): Int {
        return friendRequestList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FriendItemBinding.inflate(layoutInflater, parent, false)
        binding.acceptButton.setOnClickListener {
            //TODO - 친구신청 수락
            Log.w("Clicked", "accept Button")
        }

        binding.rejectButton.setOnClickListener {
            //TODO - 친구신청 거절
            Log.w("Clicked", "reject Button")
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.setContents(position)
    }


}