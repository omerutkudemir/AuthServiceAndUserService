package com.poslifayproject.poslifay.model

import jakarta.persistence.*

@Entity
@Table(name = "likes")
data class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // foreign key: hangi kullanıcı beğenmiş
    val user: Users,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id") // foreign key: hangi haber beğenilmiş
    val news: News
)
