package com.poslifayproject.poslifay.model

import jakarta.persistence.*
import jdk.jfr.Unsigned
import java.sql.Date
import java.time.LocalDateTime

@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val createdDate:Date,
    val commentText: String,
    @ManyToOne(fetch = FetchType.LAZY, cascade =[CascadeType.MERGE] )
    val user : Users,
    @ManyToOne(fetch = FetchType.LAZY, cascade =[CascadeType.MERGE] )
    val news:News,
    val parentCommentId:Long?,
    val likeCount:Int,

)
{

    constructor(user: Users, news: News, parentCommentId: Long?, commentText: String) : this(
        id = 0L,
        user = user,
        news = news,
        parentCommentId = parentCommentId,
        commentText = commentText,
        createdDate = Date.valueOf(LocalDateTime.now().toLocalDate()), // LocalDateTime'ı Date'e dönüştür
        likeCount = 0
    )
}