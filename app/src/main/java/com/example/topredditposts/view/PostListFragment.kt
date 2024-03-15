package com.example.topredditposts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.viewModels
import com.example.topredditposts.databinding.FragmentPostListBinding
import com.example.topredditposts.model.PostApiImpl
import com.example.topredditposts.repo.Repository
import com.example.topredditposts.retrofit.RetrofitBuilder
import kotlinx.coroutines.launch

class PostListFragment : Fragment() {

    private val adapter by lazy {
        TopRedditHolderPostsAdapter(
            activity = requireContext(),
            onLoadNextPage = viewModel::nextPage
        )
    }
    private val viewModel: DataViewModel by viewModels {
        DataViewModel.provideFactory(Repository(PostApiImpl(RetrofitBuilder.postApi)))
    }

    private var binding: FragmentPostListBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostListBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observerData()
    }

    private fun initUi() {
        binding?.apply {
            postList.adapter = adapter
        }
    }

    private fun observerData() {
        lifecycleScope.launch {
            viewModel.state.collect { value ->
                adapter.submitList(value.posts)
            }
        }
    }
}