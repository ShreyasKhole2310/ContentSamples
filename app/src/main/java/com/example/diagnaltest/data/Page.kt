package com.example.diagnaltest.data

data class Page(
    val contentItems: ContentItems,
    val pageNum: Int,
    val pageSize: Int,
    val title: String,
    val totalContentItems: Int
)