package com.example.diagnaltest.dto

import com.example.diagnaltest.data.Content
import com.example.diagnaltest.data.ContentItems
import com.google.gson.annotations.SerializedName

data class ContentItemsDto(
    val content: List<ContentDto>
){
    fun toContentItems(): ContentItems {
        return ContentItems(
            content = content.map { it.toContent() } as MutableList<Content>
        )
    }
}

data class ContentDto(
    val name: String,

    @SerializedName("poster-image")
    val posterImage: String
){
    fun toContent(): Content {
        return Content(
            name = name,
            posterImage = posterImage
        )
    }
}
