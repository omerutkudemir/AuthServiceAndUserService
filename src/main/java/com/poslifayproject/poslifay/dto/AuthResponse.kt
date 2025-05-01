package com.poslifayproject.poslifay.dto

data class AuthResponse(
    val token:String,
    val username: String,
    val userImageUrl:String
)
