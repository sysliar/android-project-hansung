package com.example.hansung_teamproject

import com.google.firebase.firestore.DocumentSnapshot

data class User(
    var name: String = "",
    var email: String = "",
    var birth: String = ""
)
