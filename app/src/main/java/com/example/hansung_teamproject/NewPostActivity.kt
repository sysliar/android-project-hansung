package com.example.hansung_teamproject

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hansung_teamproject.LoginActivity.Companion.myEmail
import com.example.hansung_teamproject.LoginActivity.Companion.myName
import com.example.hansung_teamproject.databinding.PostRegisterBinding
import com.example.hansung_teamproject.feed.FeedActivity
import com.example.hansung_teamproject.feed.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class NewPostActivity : AppCompatActivity() {
    private lateinit var binding : PostRegisterBinding
    val db = Firebase.firestore
    lateinit var storage: FirebaseStorage
    private var uploadfileName : String? = null

    companion object {
        const val REQUEST_CODE = 1
        var IMAGE_PERMISSION = ""
    }

    val postsCollectionRef = db.collection("posts")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PostRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.currentUser ?: finish()
        storage = Firebase.storage

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) IMAGE_PERMISSION = Manifest.permission.READ_MEDIA_IMAGES
        else IMAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE

        binding.textView13.text = myName
        binding.imageButton5.setOnClickListener {
            startActivity(Intent(this, FeedActivity::class.java))
        }

        binding.imageButton9.setOnClickListener {
            uploadDialog()
        }

        binding.button4.setOnClickListener {
            val content = binding.postContent.text.toString()
            if(content.isNotEmpty()) {
                val post = Post(myName, myEmail, content, 0)
                if(uploadfileName != null) {
                    post.apply {
                        img_url = uploadfileName as String;
                        timestamp = Timestamp.now()
                    }
                }
                postsCollectionRef.add(post).addOnSuccessListener {
                    Toast.makeText(
                    baseContext, "게시물 등록에 성공하였습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                    startActivity(Intent(this, FeedActivity::class.java)) }
                    .addOnFailureListener {
                        Toast.makeText(
                        baseContext, "등록에 실패 하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show() }

            }
            else {
                Toast.makeText(
                    baseContext, "텍스트 칸이 비어있습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //permission을 READ_EXTERNAL_STORAGE에서 READ_MEDIA_IMAGES로 바꾸니 사진 읽어오기는 되는데 firebase storage에는 안올라감(println("실패"))
    private fun uploadDialog() {
        if (ContextCompat.checkSelfPermission(this, IMAGE_PERMISSION)
            == PackageManager.PERMISSION_GRANTED) {
            println("권한 있음")
            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null)

            AlertDialog.Builder(this)
                .setTitle("Choose Photo")
                .setCursor(cursor, { _, i ->
                    cursor?.run {
                        moveToPosition(i)
                        val idIdx = getColumnIndex(MediaStore.Images.ImageColumns._ID)
                        val nameIdx = getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
                        println(getLong(idIdx))
                        println(getString(nameIdx))
                        uploadFile(getLong(idIdx), getString(nameIdx))
                    }
                }, MediaStore.Images.ImageColumns.DISPLAY_NAME).create().show()
        } else {
            println("권한 없음")
            ActivityCompat.requestPermissions(this, arrayOf(IMAGE_PERMISSION), REQUEST_CODE)
        }
    }

    private fun uploadFile(file_id: Long?, fileName: String?) {
        file_id ?: return
        val imageRef = storage.reference.child("upload/${fileName}")
        val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, file_id)
        imageRef.putFile(contentUri).addOnCompleteListener {
            if (it.isSuccessful) {
                // upload success
                Snackbar.make(binding.root, "Upload completed.", Snackbar.LENGTH_SHORT).show()
                uploadfileName = fileName;
                binding.textView14.text = uploadfileName
            }
            else { println("실패") }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                uploadDialog()
            }
        }
    }


}