package com.poslifayproject.poslifay.dto

import java.util.UUID

data class StatisticDto(
    val userId:UUID,
    val likedCategoryNames:List<CategoryDto>,
    val likeCount:Int
) {
}