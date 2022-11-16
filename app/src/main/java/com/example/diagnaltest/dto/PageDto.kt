package com.example.diagnaltest.dto

import com.example.diagnaltest.data.ContentItems
import com.example.diagnaltest.data.Page
import com.google.gson.annotations.SerializedName

data class PageDto(
    @SerializedName("content-items")
    val contentItems: ContentItemsDto,

    @SerializedName("page-num")
    val pageNum: Int,

    @SerializedName("page-size")
    val pageSize: Int,

    val title: String,

    @SerializedName("total-content-items")
    val totalContentItems: Int
){
    fun toPage(): Page {
        return Page(
            contentItems = contentItems.toContentItems(),
            pageNum = pageNum,
            pageSize = pageSize,
            title = title,
            totalContentItems = totalContentItems
        )
    }
}
