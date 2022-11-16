package com.example.diagnaltest.model

import android.content.Context
import com.example.diagnaltest.data.ContentListingData
import com.example.diagnaltest.dto.ContentListingDto
import com.example.diagnaltest.response.Result
import com.google.gson.Gson

object AccessJson {

    fun getListing(context: Context, file_name: String): Result<ContentListingData> {
         runCatching {
            Gson().fromJson(getDatafromJSON(context, file_name), ContentListingDto::class.java)
        }.onSuccess {
             return Result.Success(it.toListingResponse())
        }.onFailure {
            return Result.Error(it.toString())
        }
        return Result.Loading(null)
    }

    private fun getDatafromJSON(context: Context, fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e:java.lang.Exception) {
            ""
        }

    }

}