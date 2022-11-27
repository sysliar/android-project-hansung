package com.example.hansung_teamproject.feed

import com.google.firebase.Timestamp
import java.util.*


data class Post(
    val name: String = "",
    val email: String = "",
    val content: String = "",
    val like : Int = 0,
    val img_url : String = ""
)
