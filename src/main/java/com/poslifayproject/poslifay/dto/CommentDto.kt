package com.poslifayproject.poslifay.dto

data class CommentDto(
    val id:Long,
    val newsId:Long,
    val userCommentDto:UserCommentDto,
    val parentCommentId:Long,
    val content:String
) {
}