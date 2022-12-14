package com.example.hansung_teamproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.hansung_teamproject.databinding.SignupPopupBinding
import com.example.hansung_teamproject.feed.FeedActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignupDialogFragment : DialogFragment() {
    private var auth : FirebaseAuth? = null
    val db = Firebase.firestore
    val binding by lazy { SignupPopupBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = true

        auth = Firebase.auth
        return SignupPopupBinding.inflate(inflater, container, false).root
    } // view binding을 이용하여 레이아웃 inflate함

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = SignupPopupBinding.bind(view)
        binding.cancelButton.setOnClickListener {
            dismiss() // 다이얼로그를 없어지게 함
        }
        binding.submitButton.setOnClickListener {
            createAccount(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString(), binding.nameEditText.text.toString(), binding.birthEditText.text.toString())
        }
    }
    public override fun onStart() {
        super.onStart()
//        moveMainPage(auth?.currentUser)
    }
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(context as LoginActivity, FeedActivity::class.java))
            dismiss()
        }
    }
    private fun createAccount(email: String, password: String, name: String, birth: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && birth.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = User(name, email, birth)
                        db.collection("users")
                            .document(email)
                            .set(user)
                        Toast.makeText(
                            context as LoginActivity, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismiss()
                    } else {
                        Toast.makeText(
                            context as LoginActivity, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else if(email.isEmpty()) {
            Toast.makeText(
                context as LoginActivity, "이메일을 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
        } else if (password.isEmpty()) {
            Toast.makeText(
                context as LoginActivity, "비밀번호를 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
        } else if (name.isEmpty()) {
            Toast.makeText(
                context as LoginActivity, "이름을 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
        } else if (birth.isEmpty()) {
            Toast.makeText(
                context as LoginActivity, "생년월일을 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

