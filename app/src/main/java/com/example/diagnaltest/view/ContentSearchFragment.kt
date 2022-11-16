package com.example.diagnaltest.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diagnaltest.databinding.FragmentContentSearchBinding
import com.example.diagnaltest.viewmodel.ContentListingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for Searching Content
 */
class ContentSearchFragment : Fragment() {
    private val contentListingViewModel: ContentListingViewModel by viewModel()

    private var _binding: FragmentContentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentListingViewModel.getContent()

        setupRecyclerView()
        setupSearchText()

        binding.cancelSearchImageview.setOnClickListener{
            binding.searchEdittext.setText("")
        }
    }

    private fun setupRecyclerView() {
        val onClickListener = ContentListRecyclerAdapter.OnClickListener { content, _ ->
            Toast.makeText(
                requireContext(),
                "Clicked ${content.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
        val adapter = ContentListRecyclerAdapter(onClickListener)
        binding.searchRecyclerView.apply {
            layoutManager =
                if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT)
                    GridLayoutManager(context, 3)
                else GridLayoutManager(context, 5)
            this.adapter = adapter
            addItemDecoration(ContentLayoutItemDecoration())
        }

        contentListingViewModel.pageLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }

    private fun setupSearchText() {
        binding.searchEdittext.doAfterTextChanged {
            binding.cancelSearchImageview.visibility = if(it.toString() != "") {
                View.VISIBLE
            }else{
                View.GONE
            }
            if (it.toString().isNotEmpty() && it.toString().length >= 3) {
                contentListingViewModel.searchData(it.toString())
            }
        }
    }

}