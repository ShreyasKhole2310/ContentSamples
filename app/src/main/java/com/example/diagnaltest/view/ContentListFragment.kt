package com.example.diagnaltest.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diagnaltest.R
import com.example.diagnaltest.databinding.FragmentContentListBinding
import com.example.diagnaltest.viewmodel.ContentListingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Display the List of Movies/Content
 */
class ContentListFragment : Fragment() {

    private val contentListingViewModel: ContentListingViewModel by viewModel()

    private var _binding: FragmentContentListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentListingViewModel.getContent()
        setupRecyclerView()

        binding.searchImageview.setOnClickListener {
            findNavController().navigate(R.id.contentSearchFragment)
        }

        binding.backImageview.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        contentListingViewModel.pageMutable.observe(viewLifecycleOwner) { page ->
            page?.let {
                binding.titleTextview.text = it.title
            }
        }
    }

    private fun setupRecyclerView() {
        val adapter =
            ContentListRecyclerAdapter(ContentListRecyclerAdapter.OnClickListener { content, _ ->
                Toast.makeText(
                    requireContext(),
                    "Clicked ${content.name}",
                    Toast.LENGTH_SHORT
                ).show()
            })

        binding.contentListRecylerView.apply {
            layoutManager =
                if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT)
                    GridLayoutManager(context, 3)
                else GridLayoutManager(context, 5)
            addOnScrollListener(scrollRecyclerListener)
            this.adapter = adapter
            addItemDecoration(ContentLayoutItemDecoration())
        }

        contentListingViewModel.pageLiveData.observe(viewLifecycleOwner) {
            it?.let { list ->
                adapter.submitList(list)
            }
        }

    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollRecyclerListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible =
                totalItemCount >= contentListingViewModel.pageResponse?.page?.pageSize!!
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                contentListingViewModel.getContent()
                isScrolling = false
            } else {
                binding.contentListRecylerView.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}

