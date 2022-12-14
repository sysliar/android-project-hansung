package com.example.hansung_teamproject.feed

import com.google.firebase.Timestamp
import java.util.*


data class Post(
    val name: String = "",
    val email: String = "",
    val content: String = "",
    val like : Long = 0,
    var img_url : String = "",
    var timestamp: Timestamp = Timestamp(0,0),
    var id: String = ""
)
