package com.poslifayproject.poslifay.dto

import java.util.UUID

data class CommentReq(
    val newsId:Long,
    val text:String,
    val userId:UUID,
    val parentCommentId:Long?
) {
}