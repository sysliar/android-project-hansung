package com.example.hansung_teamproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hansung_teamproject.databinding.LoginBinding
import com.example.hansung_teamproject.feed.FeedActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.typeOf


private var auth : FirebaseAuth? = null
class LoginActivity : AppCompatActivity() {
    val binding by lazy { LoginBinding.inflate(layoutInflater) }
    val db = Firebase.firestore
    var temp: String = ""
    var temp2: String = ""
    companion object {
        var myName: String = ""
        var myEmail: String = ""
        var myBirth: String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        // 회원가입 창으로
        binding.signUpTextView.setOnClickListener {
//            startActivity(Intent(this,SignupDialog::class.java))
            SignupDialogFragment().show(supportFragmentManager, "signupDialog")
        }

        // 로그인 버튼
        binding.loginButton.setOnClickListener {
            signIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
        }
    }

    // 로그인
    private fun signIn(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "로그인에 성공 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        myEmail = email
                        db.collection("users").whereEqualTo("email", email).get().addOnSuccessListener { documents ->
                            for (document in documents) {
                                temp =  document.data["name"] as String
                                temp2 = document.data["birth"] as String
                            }
                            myName = temp
                            myBirth = temp2
                            moveMainPage(auth?.currentUser)
                        }
                    } else {
                        Toast.makeText(
                            baseContext, "로그인에 실패 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


    // 유저정보 넘겨주고 메인 액티비티 호출
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this, FeedActivity::class.java))
            //startActivity(Intent(this,FriendListActivity::class.java))

//            finish()
        }
    }
}