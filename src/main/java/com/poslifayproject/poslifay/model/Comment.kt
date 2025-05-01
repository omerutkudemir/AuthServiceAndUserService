package com.poslifayproject.poslifay.model

import jakarta.persistence.*
import jdk.jfr.Unsigned
import java.sql.Date

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
