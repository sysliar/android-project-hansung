package com.example.hansung_teamproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.hansung_teamproject.databinding.SignupPopupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignupDialogFragment : DialogFragment() {
    private var auth : FirebaseAuth? = null
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
            createAccount(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
        }
    }
    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(context as LoginActivity, FeedActivity::class.java))
            dismiss()
        }
    }
    private fun createAccount(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
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
        }
    }
}

