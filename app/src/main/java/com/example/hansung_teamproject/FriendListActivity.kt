package com.example.hansung_teamproject

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hansung_teamproject.databinding.FriendBinding

class FriendListActivity : AppCompatActivity() {
    //private lateinit var binding: FriendBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        //super.onCreate(savedInstanceState, persistentState)
        //binding = FriendBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.friend)

        //binding.userSearch.setOnClickListener {
        //    SignupDialogFragment().show(supportFragmentManager, "signupDialog")
        //}
    }
}