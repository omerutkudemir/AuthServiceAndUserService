package com.poslifayproject.poslifay.model

import jakarta.persistence.CascadeType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

data class Source(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long=0L,
    val sourceName:String,
    val sourceImageUrl:String,
    @OneToOne(mappedBy = "source_id", cascade = [CascadeType.ALL])
    val news: List<News>,
)