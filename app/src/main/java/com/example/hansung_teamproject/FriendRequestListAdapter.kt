package com.example.hansung_teamproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.LoginActivity.Companion.myName
import com.example.hansung_teamproject.databinding.FriendItemBinding
import com.example.hansung_teamproject.databinding.FriendListBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendRequestListAdapter : RecyclerView.Adapter<FriendRequestListAdapter.ViewHolder>() {
    var friendRequestList: ArrayList<Friend> = arrayListOf()
    val db = Firebase.firestore

    fun setFriendList(list: ArrayList<Friend>) {
        friendRequestList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: FriendItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with(friendRequestList) {
                binding.friendRequestNameTextView.text = friendRequestList[pos].name
                binding.friendRequestEmailTextView.text = friendRequestList[pos].email
            }
        }
    }

    override fun getItemCount(): Int {
        return friendRequestList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FriendItemBinding.inflate(layoutInflater, parent, false)
        binding.acceptButton.setOnClickListener { // 친구 신청 수락
            Log.w("Clicked", "accept Button")
            db.collection("users/${myEmail}/friend") // 내 db에 친구 추가
                .document("${binding.friendRequestEmailTextView.text.toString()}").set(
                    Friend(binding.friendRequestNameTextView.text.toString(), binding.friendRequestEmailTextView.text.toString()))

            db.collection("users/${binding.friendRequestEmailTextView.text.toString()}/friend").document( // 신청 상대 db에 나 추가
                myEmail).set(Friend(myName, myEmail))

            db.collection("users/${myEmail}/friend_request") // 내 신청 db 에서 해당 신청 삭제
                .document(binding.friendRequestEmailTextView.text.toString()).delete()

            friendRequestList.remove(Friend(binding.friendRequestNameTextView.text.toString(), binding.friendRequestEmailTextView.text.toString())) // view update
            Toast.makeText(
                parent.context, "친구신청 수락, 뒤로가기 후 돌아오면 친구가 추가되어 있어요!",
                Toast.LENGTH_SHORT
            ).show()

            notifyDataSetChanged()
        }

        binding.rejectButton.setOnClickListener {
            db.collection("users/${myEmail}/friend_request")
                .document(binding.friendRequestEmailTextView.text.toString()).delete()
            friendRequestList.remove(
                Friend(
                    binding.friendRequestNameTextView.text.toString(),
                    binding.friendRequestEmailTextView.text.toString()
                )
            )
            Toast.makeText(
                parent.context, "친구신청 거절",
                Toast.LENGTH_SHORT
            ).show()
            notifyDataSetChanged()
            Log.w("Clicked", "reject Button")
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.setContents(position)
    }


}