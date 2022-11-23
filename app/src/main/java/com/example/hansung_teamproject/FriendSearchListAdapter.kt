package com.example.hansung_teamproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.LoginActivity.Companion.myName
import com.example.hansung_teamproject.databinding.FriendSearchItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendSearchListAdapter : RecyclerView.Adapter<FriendSearchListAdapter.ViewHolder>() {
    var userList: ArrayList<Friend> = arrayListOf()
    val db = Firebase.firestore

    inner class ViewHolder(private val binding: FriendSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with(userList) {
                binding.friendSearchListName.text = userList[pos].name
                binding.searchUserEmailTextView.text = userList[pos].email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FriendSearchItemBinding.inflate(layoutInflater, parent, false)
        binding.friendRequestButton.setOnClickListener {
            db.collection("users/${binding.searchUserEmailTextView.text.toString()}/friend_request")
                .document("${myEmail}").set(
                Friend("${myName}", "${myEmail}")
            )
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun search(searchWord: String) {
        db?.collection("users")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                userList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    if (snapshot.getString("name")!!.contains(searchWord)) {
                        var item: Friend =
                            Friend(snapshot["name"].toString(), snapshot["email"].toString())
                        userList.add(item!!)
                    }
                }
                notifyDataSetChanged()
            }
    }
}