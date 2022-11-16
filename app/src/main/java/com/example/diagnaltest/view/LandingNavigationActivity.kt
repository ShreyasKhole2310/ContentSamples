package com.example.diagnaltest.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diagnaltest.R
import com.example.diagnaltest.data.Content
import com.example.diagnaltest.databinding.ContentListingLayoutBinding
import com.example.diagnaltest.viewmodel.ContentListingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingNavigationActivity : AppCompatActivity(){
    private var _binding: ContentListingLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ContentListingLayoutBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

    }
}