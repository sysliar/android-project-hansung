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
    var userList: ArrayList<User> = arrayListOf()
    val db = Firebase.firestore

    inner class ViewHolder(private val binding: FriendSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with(userList) {
                binding.friendSearchListName.text = userList[pos].name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FriendSearchItemBinding.inflate(layoutInflater, parent, false)
        binding.friendRequestButton.setOnClickListener {
            // firebase 친구 신청
            var friendEmail: String = ""
            db.collection("users")
                .whereEqualTo("name", binding.friendSearchListName.text.toString()).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        friendEmail = document.data["email"] as String
                    }
                    val newFriendRequest: HashMap<String, Any> = hashMapOf(
                        "name" to myName,
                        "email" to myEmail
                    )
                    db.collection("users/${friendEmail}/friend_request").add(newFriendRequest)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Friend Request Complete")
                        }

                }

        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun search(serachWord: String) {
        db?.collection("users")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                userList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    if (snapshot.getString("name")!!.contains(serachWord)) {
                        var item = snapshot.toObject(User::class.java)
                        userList.add(item!!)
                    }
                }
                notifyDataSetChanged()
            }
    }
}