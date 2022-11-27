package com.example.hansung_teamproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hansung_teamproject.LoginActivity.Companion.myBirth
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.LoginActivity.Companion.myName
import com.example.hansung_teamproject.databinding.ProfileBinding
import com.example.hansung_teamproject.databinding.ProfileSetupBinding
import com.example.hansung_teamproject.feed.FeedActivity
import com.example.hansung_teamproject.feed.Post
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileSetUpActivity : AppCompatActivity() {
    lateinit var binding: ProfileSetupBinding
    val db = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val postsCollectionRef = db.collection("posts")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView3.text = myEmail

        binding.imageButton11.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.button3.setOnClickListener {
            val modifyName = binding.nameEditText1.text.toString()
            val modifyBirth = binding.birthEditText1.text.toString()
            if(modifyName.isNotEmpty() && modifyBirth.isNotEmpty()) {
                postsCollectionRef.whereEqualTo("email", myEmail).get().addOnSuccessListener { documents ->
                    var list = mutableListOf<String>()
                    for (document in documents) {
                        list.add(document.id)
                    }
                    for(id in list) {
                        println(id)
                        postsCollectionRef.document(id).update("name", modifyName)
                    }
                }
                db.runTransaction {
                    val usersDocRef = usersCollectionRef.document(myEmail)
                    it.update(usersDocRef, "name", modifyName)
                    it.update(usersDocRef, "birth", modifyBirth)
                }.addOnSuccessListener {
                    Toast.makeText(
                        baseContext, "프로필 변경에 성공하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    myName = modifyName
                    myBirth = modifyBirth
                    startActivity(Intent(this, ProfileActivity::class.java)) }
                    .addOnFailureListener {
                        Toast.makeText(
                            baseContext, "프로필 변경에 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show() }
            }
        }
    }
}