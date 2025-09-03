package com.bytecoder.dailywaves.ui.topnews

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytecoder.dailywaves.R
import com.bytecoder.dailywaves.data.repository.NewsRepository
import com.bytecoder.dailywaves.databinding.FragmentTopNewsBinding
import com.bytecoder.dailywaves.ui.adapters.NewsAdapter
import com.bytecoder.dailywaves.util.Resource
import com.bytecoder.dailywaves.viewmodel.NewsViewModel
import com.bytecoder.dailywaves.viewmodel.NewsViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class TopNewsFragment : Fragment() {

    private var _binding: FragmentTopNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepository())
    }

    private lateinit var adapter: NewsAdapter

    // Default = India news
    private var useIndianNews = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTopNewsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) // enable menu in fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NewsAdapter { article ->
            val args = Bundle().apply {
                putString("imageUrl", article.urlToImage)
                putString("title", article.title)
                putString("content", article.content ?: article.description)
                putString("url", article.url)
            }
            findNavController().navigate(R.id.action_topNewsFragment_to_newsDetailFragment, args)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext()) // 👈 add this
            adapter = this@TopNewsFragment.adapter
            setHasFixedSize(true) // optional, for performance if item size is fixed
        }

//        binding.recyclerView.adapter = adapter

        // Pull to refresh reloads Indian news
        binding.swipeRefresh.setOnRefreshListener { loadNews() }

        observe()
        loadNews()
    }

    private fun loadNews() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar)

        if (useIndianNews) {
            viewModel.loadIndianNews()
            toolbar.title = "India News"
        } else {
            viewModel.loadTopHeadlines("us")
            toolbar.title = "US Headlines"
        }
    }

    private fun observe() {
        viewModel.articles.observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.shimmerContainer.visibility = View.VISIBLE
                    binding.shimmerContainer.startShimmer()
                    binding.stateError.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Success -> {
                    binding.shimmerContainer.stopShimmer()
                    binding.shimmerContainer.visibility = View.GONE
                    binding.stateError.visibility = View.GONE
                    adapter.submitList(res.data)
                }
                is Resource.Error -> {
                    binding.shimmerContainer.stopShimmer()
                    binding.shimmerContainer.visibility = View.GONE
                    binding.stateError.visibility = View.VISIBLE
                    binding.tvError.text = res.message
                }
            }
        }
    }

    // Inflate menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle menu clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_toggle_source -> {
                useIndianNews = !useIndianNews
                if (useIndianNews) {
                    item.setIcon(R.drawable.ic_india)
                    item.title = "India News"
                    Snackbar.make(binding.root, "Switched to India News", Snackbar.LENGTH_SHORT).show()
                } else {
                    item.setIcon(R.drawable.ic_us)
                    item.title = "US Headlines"
                    Snackbar.make(binding.root, "Switched to Top Headlines (US)", Snackbar.LENGTH_SHORT).show()
                }
                loadNews()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
