package com.poslifayproject.poslifay.model

import jakarta.persistence.*
import java.sql.Date

@Entity
data class News(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,
    val title:String,
    val content: String,
    val imageURl:String,
    @ManyToOne(fetch = FetchType.LAZY, cascade =[CascadeType.MERGE])
    @JoinColumn(name = "sourceId")
    val source: Source,
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "category_id")
    val category:Category,
    val agencyName: String,
    val publishedTime: Date,
    @OneToMany(mappedBy = "news", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes: List<Like>,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "comment_id")
    val comments:List<Comment>,
    val newsUrl:String

)
