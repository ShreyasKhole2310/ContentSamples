package com.example.diagnaltest.dto

import com.example.diagnaltest.data.ContentListingData

data class ContentListingDto(
    val page: PageDto
){
    fun toListingResponse():ContentListingData{
        return ContentListingData(
            page = page.toPage()
        )
    }
}