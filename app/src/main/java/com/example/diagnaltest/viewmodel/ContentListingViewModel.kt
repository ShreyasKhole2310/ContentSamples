package com.example.diagnaltest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diagnaltest.data.Content
import com.example.diagnaltest.data.ContentListingData
import com.example.diagnaltest.data.Page
import com.example.diagnaltest.repository.ListingRepository
import kotlinx.coroutines.launch

/**
 * Viewmodel to obtain data from
 */
class ContentListingViewModel(
    private val listingRepository: ListingRepository
) : ViewModel() {

    var pageLiveData: MutableLiveData<MutableList<Content>> = MutableLiveData()
    var pageResponse: ContentListingData? = null
    var pageMutable: MutableLiveData<Page> = MutableLiveData()

    private var pageno = 1
    private val fileNameStr: String = "CONTENTLISTINGPAGE-PAGE$pageno.json"

    // Fetching List of Content
    fun getContent() {
        viewModelScope.launch {
            listingRepository.getContenListing(fileNameStr).runCatching {
                pageno++
                pageLiveData.postValue(this.data?.let { getList(it) })

                pageMutable.postValue(this.data?.page!!)
            }.onFailure {
                Log.e("Error", it.toString())
            }
        }
    }

    fun searchData(strSearchQuery: String) {
        viewModelScope.launch {
            val searchResult = pageResponse?.page?.contentItems?.content?.filter {
                it.name.lowercase() == strSearchQuery.lowercase() ||
                        it.name.lowercase().contains(strSearchQuery.lowercase())
            }
            searchResult?.let {
                pageLiveData.postValue(it as MutableList<Content>?)
            }
        }
    }

    private fun getList(data: ContentListingData): MutableList<Content> {
        return if (pageResponse == null) {
            pageResponse = data
            pageResponse?.page?.contentItems?.content!!
        } else {
            val oldContent = pageResponse?.page?.contentItems?.content
            val newContent = data.page.contentItems.content
            newContent.let { oldContent?.addAll(it) }
            newContent
        }
    }

}