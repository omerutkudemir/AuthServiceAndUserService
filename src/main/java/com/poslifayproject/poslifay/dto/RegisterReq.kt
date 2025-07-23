package com.poslifayproject.poslifay.dto

import java.sql.Date


data class RegisterReq(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String,
    val birthDate: Date?,
    val sex:Boolean?,
    val userImage:String?,
    val age: Short

) {

}