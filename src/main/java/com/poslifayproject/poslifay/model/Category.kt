package com.poslifayproject.poslifay.model

import jakarta.persistence.*

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,
    val categoryName:String,
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val news:News
)
