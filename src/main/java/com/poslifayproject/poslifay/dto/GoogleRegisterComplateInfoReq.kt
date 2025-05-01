package com.poslifayproject.poslifay.dto

import java.sql.Date

data class GoogleRegisterComplateInfoReq(
    val birthDate:Date,
    val sex:Boolean,
    val username:String
)
