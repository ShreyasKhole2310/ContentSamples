package com.example.diagnaltest.model

import android.content.Context
import com.example.diagnaltest.data.ContentListingData
import com.example.diagnaltest.repository.ListingRepository
import com.example.diagnaltest.response.Result

class ListingRepositoryImpl(private val context: Context): ListingRepository {

    override suspend fun getContenListing(file_name:String): Result<ContentListingData> {
       return AccessJson.getListing(context, file_name)
    }

}