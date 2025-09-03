package com.bytecoder.dailywaves.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytecoder.dailywaves.R
import com.bytecoder.dailywaves.data.NewsRepository
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private val apiKey = "0663a6f4fe2e487f97141444e694a985"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this, NewsViewModelFactory(NewsRepository())).get(NewsViewModel::class.java)

        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            recyclerViewNews.adapter = NewsAdapter(articles) { article ->
                // TODO: Navigate to detail fragment
            }
        }

        viewModel.fetchNews(apiKey)
    }
}
