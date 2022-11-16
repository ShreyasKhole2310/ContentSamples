package com.example.diagnaltest.repository

import com.example.diagnaltest.data.ContentListingData
import com.example.diagnaltest.response.Result

interface ListingRepository  {
    suspend fun getContenListing(file_name: String) : Result<ContentListingData>
}