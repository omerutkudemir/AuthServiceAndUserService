package com.poslifayproject.poslifay.dto

import java.sql.Date

data class NewsDto(
    val id:Long,
    val title:String,
    val content:String,
    val category:CategoryDto,
    val imageUrl:String,
    val sourcaName: String,
    val likes:Int,
    val publishedDate:Date
) {
}